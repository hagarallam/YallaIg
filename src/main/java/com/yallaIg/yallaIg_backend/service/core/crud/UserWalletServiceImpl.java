package com.yallaIg.yallaIg_backend.service.core.crud;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.dto.request.ChargeWalletRequestDto;
import com.yallaIg.yallaIg_backend.enums.PaymentMethodEnum;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.model.UserWallet;
import com.yallaIg.yallaIg_backend.repository.UserWalletRepository;
import com.yallaIg.yallaIg_backend.service.core.payment.PaymentService;
import com.yallaIg.yallaIg_backend.service.core.payment.PaymentServiceFactory;
import com.yallaIg.yallaIg_backend.service.core.payment.model.PaymentRequest;
import com.yallaIg.yallaIg_backend.service.redis.UserPaymentCacheService;
import com.yallaIg.yallaIg_backend.service.redis.model.UserCacheData;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;

import static com.yallaIg.yallaIg_backend.service.redis.UserPaymentCacheService.KEY_NAME;

@Service
@RequiredArgsConstructor
@Transactional
public class UserWalletServiceImpl implements UserWalletService{


    private final UserWalletRepository userWalletRepository;
    private final PaymentServiceFactory paymentServiceFactory;
    private final WalletTransactionService walletTransactionService;
    private final UserPaymentCacheService userPaymentCacheService;
    private PaymentService paymentService;

    // only new users (once)
    @Override
    public void createUserWallet(User user)  {
        Date currentDate = new Date();
        UserWallet userWallet = new UserWallet();
        userWallet.setCreatedBy(user.getUserId());
        userWallet.setLastModifiedBy(user.getUserId());
        userWallet.setCreationDate(currentDate);
        userWallet.setLastModificationDate(currentDate);
        userWalletRepository.save(userWallet);
    }

    @Override
    public boolean chargeWallet(String paymentId) {
        
        UserCacheData userCacheData = userPaymentCacheService.get(KEY_NAME,paymentId);
        UserWallet userWallet = updateUserWallet(userCacheData.getAmount(),userCacheData.getUserId());
        saveWalletTransaction(userWallet,userCacheData.getAmount(),userCacheData.getPaymentMethodEnum(),paymentId,userCacheData.getUserId());
        return true;
    }

    @Override
    public String processPayment(ChargeWalletRequestDto chargeWalletRequestDto) {
        PaymentRequest paymentRequest = createPaymentRequest(chargeWalletRequestDto);

        // get the instance of payment service from Factory
        this.paymentService = paymentServiceFactory.getPaymentInstance(chargeWalletRequestDto.getPaymentMethodEnum());

        Payment payment = (Payment) paymentService.initiatePayment(paymentRequest).getPaymentResponse();

        cacheUserPaymentData(payment.getId(),chargeWalletRequestDto.getAmount(),chargeWalletRequestDto.getPaymentMethodEnum());

        return getApprovalUrl(payment);
    }

    private void cacheUserPaymentData(String paymentId, Double amount , PaymentMethodEnum paymentMethodEnum) {
        UserCacheData userCacheData = userPaymentCacheService.createUserCacheData(amount,paymentMethodEnum);
        userPaymentCacheService.put(KEY_NAME,paymentId,userCacheData,1);
    }

    public void saveWalletTransaction( UserWallet userWallet, Double amount , PaymentMethodEnum paymentMethodEnum,String paymentId,Integer userId) {
        walletTransactionService.save(userWallet,amount,paymentMethodEnum,paymentId,userId);
    }

    @Override
    public boolean executePayment(String paymentId, String payerId) {
        Payment payment = (Payment) this.paymentService.executePayment(paymentId,payerId).getPaymentResponse();
        return "approved".equals(payment.getState());
    }

    private static String getApprovalUrl(Payment payment) {
        return payment.getLinks().stream()
                .filter(link -> "approval_url".equals(link.getRel()))
                .map(Links::getHref)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Approval URL not found"));
    }

    private static PaymentRequest createPaymentRequest(ChargeWalletRequestDto chargeWalletRequestDto) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setTotal(chargeWalletRequestDto.getAmount());
        return paymentRequest;
    }


    @Override
    public synchronized UserWallet updateUserWallet(double amount,Integer currentUserId){
        //get user wallet
        UserWallet userWallet = getUserWallet(currentUserId);

        // update wallet object
        updateWallet(amount, userWallet);

        userWalletRepository.save(userWallet);
        return userWallet;
    }


    @Override
    public UserWallet updateUserWallet(double amount) {
        return updateUserWallet(amount,SecurityUtil.getCurrentUserId());
    }


    @Override
    public UserWallet getUserWallet(Integer currentUserId) {
        return userWalletRepository.findByCreatedBy(currentUserId).orElseThrow( () -> new ItemNotFoundException(ErrorConstants.ERROR_ITEM_NOT_FOUND));
    }

    private static void updateWallet(double amount, UserWallet userWallet) {
        userWallet.setBalance(userWallet.getBalance() + amount);
    }


}
