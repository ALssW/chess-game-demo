package com.alva.controller;

import com.alva.annotaion.Dispatcher;
import com.alva.annotaion.RequestHandler;
import com.alva.annotaion.RequestParma;
import com.alva.dispatcher.entity.Response;
import com.alva.entity.User;
import com.alva.entity.Verify;
import com.alva.service.IUserService;
import com.alva.service.impl.UserServiceImpl;
import com.alva.utils.PropertiesUtil;
import com.alva.utils.VerifyUtil;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-14
 */
@Dispatcher
@WebServlet("/user")
public class UserController {

	private final IUserService userService = new UserServiceImpl();

	@RequestHandler("/register")
	public Response<User> register(User user) {
		return userService.register(user);
	}

	@RequestHandler("/login")
	public Response<User> login(User user){
		return userService.login(user);
	}

	@RequestHandler("/find")
	public Response<User> find(User user){
		return userService.findById(user.getId());
	}

	@RequestHandler("/verify/get")
	public Response<Verify> getVerify() {
		return Response.ok(VerifyUtil.getVerify());
	}

	@RequestHandler("/verify/email/get")
	public Response<Void> getEmailVerify(User user) {
		return userService.sendEmailVerify(user);
	}

	@RequestHandler("/verify/code")
	public Response<User> verify(User user, Verify verify) {
		if (!VerifyUtil.verify(verify.getCode(), verify.getSeed())) {
			return Response.fail("验证失败");
		}
		return userService.resetPassword(user);
	}

	@RequestHandler("/verify/email/code")
	public Response<User> emailVerify(User user, @RequestParma("code") String code) {
		String msg = VerifyUtil.verifyEmail(user.getUsername(), code);
		if (msg != null) {
			return Response.fail(msg);
		}
		return userService.resetPassword(user);
	}

	@RequestHandler("/sexes")
	public Response<List<Map<String, Object>>> sexes(){
		return userService.sexes();
	}

	@RequestHandler("/scoreRank")
	public Response<List<User>> scoreRank(){
		return userService.scoreRank();
	}

	@RequestHandler("/img")
	public String img(@RequestParma("file") Part file) {
		String fileName = "";
		try {
			InputStream fileInput      = file.getInputStream();
			File        uploadFilePath = new File(PropertiesUtil.getProperty("path"));
			if (!uploadFilePath.exists()) {
				if (!uploadFilePath.mkdirs()) {
					throw new FileNotFoundException("路径 " + uploadFilePath.getAbsolutePath() + " 创建异常");
				}

			}

			String[] fileSuffix = file.getSubmittedFileName().split("\\.");
			fileName = UUID.randomUUID() + "." + fileSuffix[fileSuffix.length - 1];

			OutputStream fileOutput = new FileOutputStream(new File(uploadFilePath, fileName));
			IOUtils.copy(fileInput, fileOutput);
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	@RequestHandler("/img/get")
	public void getImg(@RequestParma("fileName") String fileName, HttpServletResponse resp) throws FileNotFoundException {
		File file = new File(PropertiesUtil.getProperty("path"), fileName);
		if (!file.exists()) {
			throw new FileNotFoundException("文件不存在");
		}
		try {
			InputStream         fileInput    = new FileInputStream(file);
			ServletOutputStream outputStream = resp.getOutputStream();
			IOUtils.copy(fileInput, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
