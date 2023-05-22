package com.alva.dao;

import com.alva.entity.Order;

import java.util.List;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-16
 */
public interface IOrderDao {

	int insert(Order order);

	Order queryByOrderId(String orderId);

	Order queryById(Integer id);

	int updateStatus(Order order);

	List<Order> queryRecent(Integer userId, Integer days);
}
