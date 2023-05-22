package com.alva.dao.impl;

import com.alva.dao.IUserDao;
import com.alva.dispatcher.db.QueryWrapper;
import com.alva.dispatcher.db.UpdateWrapper;
import com.alva.dispatcher.exception.SqlBuildException;
import com.alva.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-14
 */
public class UserDaoImpl implements IUserDao {

	@Override
	public boolean check(String filedName, Object value) {
		User result = null;
		try {
			QueryWrapper<User> wrapper = new QueryWrapper<>(User.class);
			result = wrapper.check(filedName, value).executeResult();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return result == null;
	}

	@Override
	public User insert(User user) {
		try {
			UpdateWrapper<User> wrapper = new UpdateWrapper<>(User.class);
			return wrapper.insert(user).executeResult();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer updatePassword(User user) {
		try {
			UpdateWrapper<User> wrapper = new UpdateWrapper<>(User.class);
			return wrapper.update(user, "password").eq("username", user.getUsername()).execute();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public User queryByUsernameAndPassword(User user) {
		try {
			QueryWrapper<User> wrapper = new QueryWrapper<>(User.class);
			return wrapper.selectAllBy(new String[]{"username", "password"},
					new Object[]{user.getUsername(), user.getPassword()}).executeResult();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User queryByUsername(User user) {
		try {
			QueryWrapper<User> wrapper = new QueryWrapper<>(User.class);
			wrapper.selectAllBy(new String[]{"username"},
					new Object[]{user.getUsername()}).executeResult();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer updateScore(Integer id, Long score) {
		try {
			UpdateWrapper<User> wrapper = new UpdateWrapper<>(User.class);
			return wrapper.update("score = score + ?",
					score).eq("id", id).execute();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> querySexes() {
		try {
			QueryWrapper<User> wrapper = new QueryWrapper<>(User.class);
			wrapper.select("sex", "count(sex) count").from();
			return wrapper.groupBy("sex").executeGroup();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> queryUserListOrderBy(String orderBy, String type, Integer limit) {
		try {
			QueryWrapper<User> wrapper = new QueryWrapper<>(User.class);
			wrapper.select("nickname", "score").from();
			return wrapper.orderBy(orderBy, type).limit(limit).executeList();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User queryById(Integer id) {
		try {
			QueryWrapper<User> wrapper = new QueryWrapper<>(User.class);
			return wrapper.selectAllById(id).executeResult();
		} catch (SqlBuildException e) {
			e.printStackTrace();
		}
		return null;
	}
}
