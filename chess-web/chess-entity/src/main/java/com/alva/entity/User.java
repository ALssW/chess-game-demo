package com.alva.entity;

import com.alva.annotaion.Id;
import com.alva.annotaion.Table;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-14
 */
@Table(value = "user", hsaBase = true)
public class User extends BaseEntity {

	@Id
	private Integer id;
	private String  username;
	private String  password;
	private String  nickname;
	private Integer sex;
	private String  header;
	private String  email;
	private String  phone;
	private Integer score;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
