package com.alva.service;

import com.alva.dispatcher.entity.Response;
import com.alva.entity.Order;

import java.util.List;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-16
 */
public interface IOrderService {

	Response<String> create(Integer uid, Long price);

	String pay(String orderId);

	Order findById(Integer id);

	Response<String> updateStatus(Order order);

	Response<List<Order>> recentOrder(Integer userId, Integer days);
}
