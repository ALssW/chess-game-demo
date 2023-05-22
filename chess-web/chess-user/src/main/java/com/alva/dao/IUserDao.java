package com.alva.dao;

import com.alva.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-14
 */
public interface IUserDao {
	boolean check(String filedName, Object value);

	User queryByUsernameAndPassword(User user);

	User insert(User user);

	Integer updatePassword(User user);

	User queryByUsername(User user);

	Integer updateScore(Integer userId, Long score);

	List<Map<String, Object>>  querySexes();

	List<User> queryUserListOrderBy(String orderBy, String type, Integer limit);

	User queryById(Integer id);
}
