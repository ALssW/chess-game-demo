package com.alva.controller;

import com.alipay.api.AlipayApiException;
import com.alva.annotaion.Dispatcher;
import com.alva.annotaion.PageResponse;
import com.alva.annotaion.RequestHandler;
import com.alva.annotaion.RequestParma;
import com.alva.dispatcher.entity.Page;
import com.alva.dispatcher.entity.Response;
import com.alva.entity.Order;
import com.alva.entity.ScoreDetail;
import com.alva.service.IOrderService;
import com.alva.service.impl.OrderServiceImpl;
import com.alva.utils.AlipayUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-16
 */
@Dispatcher
@WebServlet("/order")
public class OrderController {

	private final IOrderService orderService = new OrderServiceImpl();

	@RequestHandler("/create")
	public Response<String> create(@RequestParma("uid") Integer uid, @RequestParma("price") Long price) {
		return orderService.create(uid, price);
	}

	@RequestHandler(value = "/pay", contentType = Response.CONTENT_TYPE_HTML)
	public String pay(@RequestParma("orderId") String orderId) {
		return orderService.pay(orderId);
	}

	@RequestHandler("/unused/pay/result")
	public Response<String> payResult(@RequestParma("out_trade_no") String orderId) {
		Order order = new Order();
		order.setOrderId(orderId);
		order.setStatus(1);
		return orderService.updateStatus(order);
	}

	@RequestHandler("/pay/result")
	public Response<String> payResult(HttpServletRequest request) {
			Map<String, String> collect = request.getParameterMap().entrySet().stream()
				// .filter(entry -> !"method".equals(entry.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()[0]));
		try {
			boolean flag = AlipayUtil.rsaCheckV1(collect);
			System.out.println(flag);
			if (flag) {
				Order order = new Order();
				order.setOrderId(collect.get("out_trade_no"));
				return orderService.updateStatus(order);
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return Response.fail("支付失败");
	}

	@PageResponse(value = Order.class, eqSql = "user_id = ?", eqBy = "userId")
	@RequestHandler("/page")
	public void page(Page<ScoreDetail> page) {
	}


	@RequestHandler("/recent")
	public Response<List<Order>> recentOrder(@RequestParma("userId") Integer userId, @RequestParma("days") Integer days) {
		return orderService.recentOrder(userId, days);
	}

}
