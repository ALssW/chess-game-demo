package com.alva.game.entity;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-20
 */
public class RoomVO {

	private Integer id;
	private String title;
	private String owner;
	private Integer hasPassword;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getHasPassword() {
		return hasPassword;
	}

	public void setHasPassword(Integer hasPassword) {
		this.hasPassword = hasPassword;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RoomVO{" +
				"id=" + id +
				", title='" + title + '\'' +
				", owner='" + owner + '\'' +
				", hasPassword=" + hasPassword +
				", status=" + status +
				'}';
	}
}
