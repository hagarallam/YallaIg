package com.yallaIg.yallaIg_backend.service.core.enrollment;

import com.yallaIg.yallaIg_backend.dto.response.CourseResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.OrderDetailsResponseDto;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.CourseMapper;
import com.yallaIg.yallaIg_backend.mapper.OrderMapper;
import com.yallaIg.yallaIg_backend.model.Course;
import com.yallaIg.yallaIg_backend.model.Order;
import com.yallaIg.yallaIg_backend.model.StudentCourse;
import com.yallaIg.yallaIg_backend.repository.CourseRepository;
import com.yallaIg.yallaIg_backend.repository.OrderRepository;
import com.yallaIg.yallaIg_backend.service.core.crud.StudentCourseService;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderMapper orderMapper;
    private final CourseMapper courseMapper;
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseService studentCourseService;


    public Page<OrderDetailsResponseDto> getAllOrdersByUser(int page, int size){
        Page<Order> orders = getOrdersByUserName(page, size);
        Page<OrderDetailsResponseDto> orderDetailsResponseDtos = orders.map(orderMapper::orderToOrderDetailsResponseDto);
        updateOrderDetailsResponseDtoData(orderDetailsResponseDtos);
        return orderDetailsResponseDtos;
    }

    private void updateOrderDetailsResponseDtoData(Page<OrderDetailsResponseDto> orderDetailsResponseDtos) {
        orderDetailsResponseDtos.stream().forEach(order -> {
            List<CourseResponseDto> courses = getRegisteredCoursesByOrdersIds(order.getOrderId());
            order.setRegisteredCourses(courses);
        });
    }

    private List<CourseResponseDto> getRegisteredCoursesByOrdersIds(Integer orderId) {
        List<StudentCourse> studentCourseList = getStudentCourseByOrderId(orderId);
        List<Course> courses = courseRepository.findAllById(studentCourseList.stream().map(studentCourse -> studentCourse.getCourse().getCourseId()).toList());
        return courses.stream().map(courseMapper::courseToCourseResponseDto).toList();
    }

    private List<StudentCourse> getStudentCourseByOrderId(Integer orderId) {
        return studentCourseService.getByOrderId(orderId);
    }

    private Page<Order> getOrdersByUserName(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page, size);
        Integer userId = SecurityUtil.getCurrentUserId();
        Page<Order> orders = orderRepository.findAllByCreatedBy(userId,pageable);
        return orders;
    }

    public OrderDetailsResponseDto getOrder(Integer id){
        Optional<Order> order = orderRepository.findById(id);
        return order.map(orderMapper::orderToOrderDetailsResponseDto).orElseThrow(() -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
    }

    @Override
    public Integer createOrder(double totalPrice) {
        Order order = new Order();
        order.setTotalPrice(totalPrice);
        return saveOrder(order);
    }

    private int saveOrder(Order order) {
        return orderRepository.save(order).getOrderId();
    }

}
