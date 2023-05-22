package com.alva.entity;

import com.alva.annotaion.Id;
import com.alva.annotaion.Table;
import com.alva.annotaion.TableAlias;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-14
 */
@Table(value = "order", hsaBase = true)
public class Order extends BaseEntity{

	@Id
	private Integer id;
	@TableAlias("order_id")
	private String orderId;
	@TableAlias("user_id")
	private Integer userId;
	private Long price;
	private Long score;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
}
