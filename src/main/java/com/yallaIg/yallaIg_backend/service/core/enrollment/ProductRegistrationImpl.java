package com.yallaIg.yallaIg_backend.service.core.enrollment;

import com.yallaIg.yallaIg_backend.dto.request.ProductRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.ProductRegistrationResponseDto;
import com.yallaIg.yallaIg_backend.exception.BusinessValidationException;
import com.yallaIg.yallaIg_backend.exception.GeneralValidationException;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.model.Course;
import com.yallaIg.yallaIg_backend.repository.CourseRepository;
import com.yallaIg.yallaIg_backend.service.core.crud.StudentCourseService;
import com.yallaIg.yallaIg_backend.service.core.crud.UserWalletService;
import com.yallaIg.yallaIg_backend.service.core.enrollment.models.EnrollmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;
import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_REGISTER_COURSE;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductRegistrationImpl implements ProductRegistration {


    private final OrderService orderService;
    private final UserWalletService userWalletService;
    private final CourseEnrollmentService courseEnrollmentService;
    private final CourseRepository courseRepository;
    private final StudentCourseService studentCourseService;

    @Override
    public ProductRegistrationResponseDto register(ProductRegistrationRequestDto productRegistrationRequestDto) {

        if(isCourseRegisteredByUser(productRegistrationRequestDto.getProductId()))
            throw new BusinessValidationException(ERROR_REGISTER_COURSE);

        // calculate price based on the product id
        double price = calculatePrice(productRegistrationRequestDto.getProductId());

        //create and save order
        Integer orderId = orderService.createOrder(price);

        // update wallet
        updateWallet(price);

        // create and save StudentCourse Object
        EnrollmentResponse enrollmentResponse = courseEnrollmentService.enroll(productRegistrationRequestDto, orderId);


        return createProductRegistrationResponseDto(enrollmentResponse);
    }

    private boolean isCourseRegisteredByUser(int courseId) {
        return studentCourseService.checkIfRegisterdCourse(courseId);
    }

    private double calculatePrice(Integer productId) {
        Optional<Course> course = courseRepository.findById(productId);
        return course.map(Course::getPrice).orElseThrow(() -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
    }


    private void updateWallet(double totalPrice) {
        userWalletService.updateUserWallet(-totalPrice);
    }

    private ProductRegistrationResponseDto createProductRegistrationResponseDto(EnrollmentResponse enrollmentResponse) {
        ProductRegistrationResponseDto productRegistrationResponseDto = new ProductRegistrationResponseDto();
        productRegistrationResponseDto.setMessage(createMessage(enrollmentResponse));
        productRegistrationResponseDto.setLink(enrollmentResponse.getLink());
        return productRegistrationResponseDto;
    }

    private String createMessage(EnrollmentResponse enrollmentResponse) {
        StringBuilder message = new StringBuilder();
        message.append("Congratulations, ")
                .append(enrollmentResponse.getUserFullName())
                .append("! You have successfully registered for ")
                .append(enrollmentResponse.getCourseName())
                .append(". Your course will start on ")
                .append(enrollmentResponse.getStartDate())
                .append(" and will end on ")
                .append(enrollmentResponse.getEndDate())
                .append(". You can join the course meeting using the following link: ")
                .append(enrollmentResponse.getLink())
                .append(".");

        return message.toString();
    }


}
