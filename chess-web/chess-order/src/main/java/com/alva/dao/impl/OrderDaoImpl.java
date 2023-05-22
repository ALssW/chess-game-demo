package com.alva.dao.impl;

import com.alva.dao.IOrderDao;
import com.alva.dispatcher.db.QueryWrapper;
import com.alva.dispatcher.db.UpdateWrapper;
import com.alva.dispatcher.exception.SqlBuildException;
import com.alva.entity.Order;

import java.util.List;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-16
 */
public class OrderDaoImpl implements IOrderDao {
	@Override
	public int insert(Order order) {
		try {
			UpdateWrapper<Order> wrapper = new UpdateWrapper<>(Order.class);
			return wrapper.insert(order).execute();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public Order queryByOrderId(String orderId) {
		try {
			QueryWrapper<Order> wrapper = new QueryWrapper<>(Order.class);
			return wrapper.selectAllBy(
					new String[]{"order_id"},
					new Object[]{orderId})
					.executeResult();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Order queryById(Integer id) {
		try {
			QueryWrapper<Order> wrapper = new QueryWrapper<>(Order.class);
			return wrapper.selectAllById(id).executeResult();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updateStatus(Order order) {
		try {
			UpdateWrapper<Order> wrapper = new UpdateWrapper<>(Order.class);
			return wrapper.update(new String[]{"status"}, new Object[]{order.getStatus()}).eq("order_id", order.getOrderId()).execute();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Order> queryRecent(Integer userId, Integer days) {

		try {
			QueryWrapper<Order> wrapper = new QueryWrapper<>(Order.class);
			wrapper.appendSql("select CAST(ifnull(t2.value, 0) AS SIGNED) as 'price', CAST(t1.name AS DATETIME) 'create_time' from \n" +
					"(select date_format(now(), '%Y-%m-%d') as 'name'\n" +
					"union all\n" +
					"select date_format(date_add(now(),interval -1 day), '%Y-%m-%d')\n" +
					"union all\n" +
					"select date_format(date_add(now(),interval -2 day), '%Y-%m-%d')\n" +
					"union all\n" +
					"select date_format(date_add(now(),interval -3 day), '%Y-%m-%d')\n" +
					"union all\n" +
					"select date_format(date_add(now(),interval -4 day), '%Y-%m-%d')\n" +
					"union all\n" +
					"select date_format(date_add(now(),interval -5 day), '%Y-%m-%d')\n" +
					"union all\n" +
					"select date_format(date_add(now(),interval -6 day), '%Y-%m-%d')\n" +
					"union all\n" +
					"select date_format(date_add(now(),interval -7 day), '%Y-%m-%d')\n" +
					"union all\n" +
					"select date_format(date_add(now(),interval -8 day), '%Y-%m-%d')\n" +
					"union all\n" +
					"select date_format(date_add(now(),interval -9 day), '%Y-%m-%d')) t1 left join\n" +
					"(\n" +
					"\n" +
					"\tselect date_format(create_time, '%Y-%m-%d') as 'name', sum(score) as 'value' from `order`\n" +
					"\twhere user_id = ? and datediff(now(), create_time) < ? and status = 1 group by date_format(create_time, '%Y-%m-%d')\n" +
					"\n" +
					") t2 on t2.name = t1.name order by create_time ASC", userId, days);
			return wrapper.executeList();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return null;
	}
}
