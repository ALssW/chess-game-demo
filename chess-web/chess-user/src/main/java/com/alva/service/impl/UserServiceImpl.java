package com.alva.service.impl;

import com.alva.dao.IUserDao;
import com.alva.dao.impl.UserDaoImpl;
import com.alva.dispatcher.entity.Response;
import com.alva.entity.Email;
import com.alva.entity.ScoreDetail;
import com.alva.entity.User;
import com.alva.service.IScoreDetailService;
import com.alva.service.IUserService;
import com.alva.utils.Logger;
import com.alva.utils.Md5Util;
import com.alva.utils.SqlUtil;
import com.alva.utils.VerifyUtil;

import java.util.List;
import java.util.Map;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-14
 */
public class UserServiceImpl implements IUserService {

	Logger<UserServiceImpl> logger = new Logger<>(UserServiceImpl.class);
	private final IUserDao            userDao            = new UserDaoImpl();
	private final IScoreDetailService scoreDetailService = new ScoreDetailServiceImpl();

	@Override
	public Response<User> register(User user) {
		if (!userDao.check("username", user.getUsername())) {
			logger.info("账号已使用");
			return Response.fail("账号已使用");
		}
		if (!userDao.check("email", user.getEmail())) {
			logger.info("邮箱已使用");
			return Response.fail("邮箱已使用");
		}

		if (!userDao.check("phone", user.getPhone())) {
			logger.info("手机号已使用");
			return Response.fail("手机号已使用");
		}

		user.setPassword(Md5Util.getMd5(user.getPassword()));
		user.setScore(0);
		User result = userDao.insert(user);
		if (result == null) {
			logger.info("注册失败");
			return Response.fail("注册失败");
		}

		logger.info("注册成功");
		return Response.ok("注册成功");
	}

	@Override
	public Response<User> login(User user) {
		user.setPassword(Md5Util.getMd5(user.getPassword()));
		User findUser = userDao.queryByUsernameAndPassword(user);
		if (findUser == null) {
			logger.info("登录失败");
			return Response.fail("登录失败");
		}
		findUser.setPassword(null);
		logger.info("登录成功");
		return Response.ok("登录成功", findUser);
	}

	@Override
	public Response<User> resetPassword(User user) {
		if (userDao.check("username", user.getUsername())) {
			logger.info("账号不存在");
			return Response.fail("账号不存在");
		}
		user.setPassword(Md5Util.getMd5(user.getPassword()));
		int result = userDao.updatePassword(user);
		if (result == 0) {
			logger.info("修改失败");
			return Response.fail("修改失败");
		}
		logger.info("修改成功");
		return Response.ok("修改成功");
	}

	@Override
	public Response<Void> sendEmailVerify(User user) {
		User findUser = userDao.queryByUsername(user);
		if (findUser == null) {
			logger.info("账号不存在");
			return Response.fail("账号不存在");
		}
		String email = findUser.getEmail();
		VerifyUtil.sendEmail(new Email("峰迷五子棋，有胆你就来",
				email,
				"你的验证码为: " + VerifyUtil.getTtlVerify(findUser.getUsername())));
		email = email.replaceAll("(?<=(^.{3})).*@", "******@");
		return Response.ok("验证码已发送至" + email);
	}

	@Override
	public Response<String> updateScore(Integer id, Long score, String bizId, Integer source) {
		SqlUtil.begin();
		if (userDao.updateScore(id, score) != 1) {
			logger.info("更新积分与新增积分流水失败 1");
			SqlUtil.rollback();
			return Response.fail("更新积分与新增积分流水失败 1");
		}

		ScoreDetail scoreDetail = new ScoreDetail();
				scoreDetail.setUserId(id);
				scoreDetail.setScore(Math.abs(score));
				scoreDetail.setAction(score > 0 ? 0 : 1);
				scoreDetail.setSource(source);
				scoreDetail.setBusinessId(bizId);
		if (Response.CODE_FAIL.equals(scoreDetailService.create(scoreDetail).getCode())) {
			logger.info("更新积分与新增积分流水失败 2");
			SqlUtil.rollback();
			return Response.fail("更新积分与新增积分流水失败 2");
		}

		SqlUtil.commit();
		logger.info("更新积分与新增积分流水成功");
		return Response.ok("更新积分与新增积分流水成功");
	}

	@Override
	public Response<List<Map<String, Object>>> sexes() {
		List<Map<String, Object>> sexes = userDao.querySexes();
		if (sexes != null && sexes.size() != 0){
			logger.info("获取性别数成功");
			return Response.ok("获取性别数成功", sexes);
		}

		logger.info("获取性别数失败");
		return Response.fail("获取性别数失败");
	}

	@Override
	public Response<List<User>> scoreRank() {
		List<User> users = userDao.queryUserListOrderBy("score", "DESC", 10);
		if (users != null) {
			logger.info("获取积分排名成功");
			return Response.ok("获取积分排名成功", users);
		}
		logger.info("获取积分排名失败");
		return Response.fail("获取积分排名失败");
	}

	@Override
	public Response<User> findById(Integer id) {
		User user = userDao.queryById(id);
		if (user == null) {
			logger.info("查询用户失败");
			return Response.fail("查询用户失败");
		}
		user.setPassword(null);
		logger.info("查询用户成功");
		return Response.ok("查询用户成功", user);
	}
}
