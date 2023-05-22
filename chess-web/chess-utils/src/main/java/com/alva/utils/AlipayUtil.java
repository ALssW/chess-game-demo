package com.alva.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;

import java.util.Map;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-16
 */
public class AlipayUtil {
	private static final AlipayClient ALIPAY_CLIENT;

	private static final String APP_ID;
	private static final String PRIVATE_KEY;
	private static final String ALI_PUBLIC_KEY;
	private static final String PUBLIC_KEY;

	static {
		APP_ID = PropertiesUtil.getProperty("alipay.my.app_id");
		PRIVATE_KEY = PropertiesUtil.getProperty("alipay.my.private_key");
		ALI_PUBLIC_KEY = PropertiesUtil.getProperty("alipay.public_key");
		PUBLIC_KEY = PropertiesUtil.getProperty("alipay.my.public_key");

		ALIPAY_CLIENT = new DefaultAlipayClient(
				"https://openapi.alipaydev.com/gateway.do",
				APP_ID,
				PRIVATE_KEY,
				"json",
				"UTF-8",
				ALI_PUBLIC_KEY,
				"RSA2");
	}

	public static AlipayClient getAlipayClient() {
		return ALIPAY_CLIENT;
	}

	public static boolean rsaCheckV1(Map<String, String> map) throws AlipayApiException {
		return AlipaySignature.rsaCheckV1(map, ALI_PUBLIC_KEY, "UTF-8", "RSA2");
	}

}
