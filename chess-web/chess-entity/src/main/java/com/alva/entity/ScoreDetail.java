package com.alva.entity;

import com.alva.annotaion.Id;
import com.alva.annotaion.Table;
import com.alva.annotaion.TableAlias;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-14
 */@Table(value = "score_detail", hsaBase = true)
public class ScoreDetail extends BaseEntity{

	@Id
	private Integer id;
	@TableAlias("user_id")
	private Integer userId;
	@TableAlias("business_id")
	private String businessId;
	private Integer source;
	private Long score;
	private Integer action;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}
}
