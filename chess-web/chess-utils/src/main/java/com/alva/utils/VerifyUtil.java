package com.alva.utils;

import com.alva.entity.Email;
import com.alva.entity.Verify;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-07
 */
public class VerifyUtil {

	private static final Logger<VerifyUtil>              logger    = new Logger<>(VerifyUtil.class);
	private static final int                             CODE_SIZE = 4;
	private static final Map<String, TtlCode> TTL_MAP   = new ConcurrentHashMap<>();


	public static Verify getVerify() {
		return getVerify(System.currentTimeMillis());
	}

	public static Verify getVerify(Long seed) {
		Verify verify = new Verify();
		verify.setSeed(seed);
		verify.setCode(getCode(seed));
		logger.info("生成验证码 - [%s, %s]", verify.getCode(), verify.getSeed());
		return verify;
	}

	private static String getCode(Long seed) {
		StringBuilder codes = new StringBuilder(CODE_SIZE);
		Random        r     = new Random(seed);
		for (int i = 0; i < CODE_SIZE; i++) {
			char code;

			boolean isCode = r.nextBoolean();
			if (!isCode) {
				code = (char) (r.nextInt(10) + 48);
				codes.append(code);
				continue;
			}

			int offset = r.nextInt(26);
			code = (char) ('a' + offset);

			boolean isUpper = r.nextBoolean();
			if (isUpper) {
				code = Character.toUpperCase(code);
			}
			codes.append(code);
		}
		return codes.toString();
	}

	public static String getTtlVerify(String username) {
		long   begin = System.currentTimeMillis();
		String code  = getCode(begin);
		TTL_MAP.put(username, new TtlCode(code, begin));
		return code;
	}

	public static boolean verify(String code, Long seed) {
		logger.info("本次验证耗费时间 [%s]:", between(seed));

		return code.equals(getVerify(seed).getCode());
	}

	public static String verifyEmail(String username, String code) {
		TtlCode ttlCode = TTL_MAP.get(username);
		if (ttlCode == null) {
			return "不存在该验证码";
		}
		Long begin = ttlCode.begin;

		int seconds = getSeconds(begin);
		int ttl     = Integer.parseInt(PropertiesUtil.getProperty("verify.code.ttl"));
		if (ttl < seconds) {
			return "验证码已过期";
		}

		if (!ttlCode.getCode().equals(code)) {
			return "验证码错误";
		}

		TTL_MAP.remove(username);
		return null;
	}

	public static void sendEmail(Email email) {
		ThreadPoolUtil.submit(() -> {
			//1、创建Session对象
			Session session = Session.getDefaultInstance(PropertiesUtil.getApplicationProperties());
			//2、创建一封邮件
			MimeMessage mimeMessage = new MimeMessage(session);

			try {
				//3、给邮件设置相关的属性
				//设置标题
				mimeMessage.setSubject(email.getSubject(), "UTF-8");
				//发件人
				mimeMessage.setFrom(new InternetAddress(PropertiesUtil.getProperty("mail.from")));
				//收件人
				//接收者的类型
				// - Message.RecipientType.TO 表示普通接收者
				// - Message.RecipientType.CC 表示抄送者
				// - Message.RecipientType.BCC 表示密送者
				mimeMessage.setRecipients(Message.RecipientType.TO, email.getTo());
				//内容
				mimeMessage.setContent(email.getContent(), "text/html;charset=utf-8");

				//4、通过session发送邮件
				Transport transport = session.getTransport();
				//5、链接SMTP服务器 通过账号密码
				transport.connect(PropertiesUtil.getProperty("mail.user"),
						PropertiesUtil.getProperty("mail.pass"));
				transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
				System.out.println("邮件发送完成！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static Integer getSeconds(Long begin) {
		Long now     = System.currentTimeMillis();
		long between = now - begin;
		return Math.toIntExact(between / 1000);
	}

	private static String between(Long begin) {
		int second  = getSeconds(begin) % 60;
		int minutes = Math.toIntExact(System.currentTimeMillis() - begin / 60000 % 60);

		return (minutes > 10 ? String.valueOf(minutes) : "0" + minutes) + "分" +
				(second > 10 ? String.valueOf(second) : "0" + second) + "秒";
	}

	private static class TtlCode {
		private String code;
		private Long   begin;

		public TtlCode() {
		}

		public TtlCode(String code, Long begin) {
			this.code = code;
			this.begin = begin;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Long getBegin() {
			return begin;
		}

		public void setBegin(Long begin) {
			this.begin = begin;
		}
	}

}
