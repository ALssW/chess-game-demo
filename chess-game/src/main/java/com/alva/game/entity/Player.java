package com.alva.game.entity;

import com.alibaba.fastjson2.annotation.JSONType;

import javax.websocket.Session;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-20
 */
@JSONType(ignores = "session")
public class Player {

	private Integer id;
	private String header;
	private String nickname;
	private Session session;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
