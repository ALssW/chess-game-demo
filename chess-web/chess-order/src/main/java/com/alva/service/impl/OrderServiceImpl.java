package com.alva.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alva.dao.IOrderDao;
import com.alva.dao.impl.OrderDaoImpl;
import com.alva.dispatcher.entity.Response;
import com.alva.entity.Order;
import com.alva.service.IOrderService;
import com.alva.service.IUserService;
import com.alva.utils.AlipayUtil;
import com.alva.utils.Logger;
import com.alva.utils.SqlUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-16
 */
public class OrderServiceImpl implements IOrderService {
	Logger<OrderServiceImpl> logger = new Logger<>(OrderServiceImpl.class);

	private final IOrderDao orderDao = new OrderDaoImpl();

	private final IUserService userService = new UserServiceImpl();

	@Override
	public Response<String> create(Integer uid, Long price) {
		String orderId = UUID.randomUUID().toString();
		Order  order   = new Order();
		order.setOrderId(orderId);
		order.setUserId(uid);
		order.setPrice(price);
		order.setScore(price * 100);
		int result = orderDao.insert(order);
		if (result != 0) {
			return Response.ok("创建订单成功", orderId);
		}
		return Response.fail("创建订单失败");
	}

	@Override
	public String pay(String orderId) {
		Order resultOrder = orderDao.queryByOrderId(orderId);
		if (resultOrder != null) {
			AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
			request.setBizModel(new AlipayTradePrecreateModel());
			request.setNotifyUrl("http://4db1c19a.r5.cpolar.top/order/pay/result");
			JSONObject bizContent = new JSONObject();
			bizContent.put("out_trade_no", resultOrder.getOrderId());
			bizContent.put("total_amount", resultOrder.getPrice());
			bizContent.put("subject", "买啥啊");
			bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

			request.setBizContent(bizContent.toString());
			AlipayTradePagePayResponse response;
			try {
				response = AlipayUtil.getAlipayClient().pageExecute(request);

				if (response.isSuccess()) {
					return response.getBody();
				} else {
					return "冲！充大钱!";
				}
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
		}
		return "充锤子";
	}

	@Override
	public Order findById(Integer id) {
		return orderDao.queryById(id);
	}

	@Override
	public Response<String> updateStatus(Order order) {
		SqlUtil.begin();
		if (orderDao.updateStatus(order) != 1) {
			SqlUtil.rollback();
			return Response.fail("更新订单状态失败 1");
		}

		Order resultOrder = orderDao.queryByOrderId(order.getOrderId());
		if (resultOrder == null) {
			SqlUtil.rollback();
			return Response.fail("更新订单状态失败 2");
		}
		Response<String> updateScore = userService.updateScore(resultOrder.getUserId(), resultOrder.getScore(),
				order.getOrderId(), 0);
		if (Response.CODE_FAIL.equals(updateScore.getCode())) {
			SqlUtil.rollback();
			return Response.fail("更新订单状态失败 3");
		}

		SqlUtil.commit();
		return Response.ok("更新订单状态成功");
	}

	@Override
	public Response<List<Order>> recentOrder(Integer userId, Integer days) {
		List<Order> orders = orderDao.queryRecent(userId, days);
		if (orders == null) {
			logger.info("获取最近订单失败");
			return Response.fail("获取最近订单失败");
		}

		logger.info("获取积最近订单成功");
		return Response.ok("获取最近订单成功", orders);
	}
}
