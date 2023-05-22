package com.alva.entity;

import com.alva.annotaion.TableAlias;

import java.time.LocalDateTime;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-14
 */
public class BaseEntity {

	private Integer       status     = 0;
	@TableAlias("create_time")
	private LocalDateTime createTime = LocalDateTime.now();
	@TableAlias("update_time")
	private LocalDateTime updateTime = LocalDateTime.now();
	@TableAlias("is_delete")
	private Integer       isDelete   = 0;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}
