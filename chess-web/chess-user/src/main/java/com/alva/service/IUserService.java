package com.alva.service;

import com.alva.dispatcher.entity.Response;
import com.alva.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-14
 */
public interface IUserService {
	Response<User> register(User user);

	Response<User> login(User user);

	Response<User> resetPassword(User user);

	Response<Void> sendEmailVerify(User user);

	Response<String> updateScore(Integer userId, Long score, String orderId, Integer source);

	Response<List<Map<String, Object>>> sexes();

	Response<List<User>> scoreRank();

	Response<User> findById(Integer id);
}
