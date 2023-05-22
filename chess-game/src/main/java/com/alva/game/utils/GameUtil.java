package com.alva.game.utils;

import com.alibaba.fastjson2.JSON;
import com.alva.game.entity.GameMessage;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-20
 */
public class GameUtil {

	public static void sendMsg(Session session, GameMessage message){
		try {
			String msg = JSON.toJSONString(message);
			System.out.println("向客户端发送一条消息: " + msg);
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
