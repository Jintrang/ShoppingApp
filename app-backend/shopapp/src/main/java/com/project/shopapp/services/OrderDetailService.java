package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
            OrderDetail newOrderDetail= OrderDetail.builder()
                    .product(productRepository.findById(orderDetailDTO.getProductID())
                            .orElseThrow(()->new DataNotFoundException("Product not found")))
                    .color(orderDetailDTO.getColor())
                    .price(orderDetailDTO.getPrice())
                    .numberOfProducts(orderDetailDTO.getNumberOfProduct())
                    .totalMoney(orderDetailDTO.getTotalMoney())
                    .order(orderRepository.findById(orderDetailDTO.getOrderID())
                            .orElseThrow(() -> new DataNotFoundException("Order not found")))
                    .build();
            orderDetailRepository.save(newOrderDetail);
        return newOrderDetail;
    }

    @Override
    public OrderDetail updateOrderDetail(long orderDetailID, OrderDetailDTO orderDetailDTO) throws Exception {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(orderDetailID)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductID())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderID())
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        existingOrderDetail.setProduct(existingProduct);
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProduct());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public OrderDetail getOrderDetailById(long id) throws Exception {
        return orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrderId(long orderId) throws Exception {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public ResponseEntity<?> deleteOrderDetailById(long id) throws Exception {
        orderDetailRepository.deleteById(id);
        return ResponseEntity.ok().body("Order detail deleted successfully with id: " + id);
    }

}
