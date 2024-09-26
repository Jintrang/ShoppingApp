package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.OrderDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail updateOrderDetail(long orderDeatailID,OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail getOrderDetailById(long id) throws Exception;
    List<OrderDetail> getOrderDetailByOrderId(long orderId) throws Exception;
    ResponseEntity<?> deleteOrderDetailById(long id) throws Exception;
}
