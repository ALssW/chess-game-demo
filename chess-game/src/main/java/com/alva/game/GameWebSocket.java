package com.alva.game;

import com.alibaba.fastjson2.JSON;
import com.alva.game.entity.ChessBoard;
import com.alva.game.entity.GameMessage;
import com.alva.game.entity.Player;
import com.alva.game.entity.Room;
import com.alva.game.utils.GameUtil;
import com.alva.game.utils.RoomUtil;
import com.alva.service.IUserService;
import com.alva.service.impl.UserServiceImpl;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-20
 */
@ServerEndpoint("/demo/chess/wss/chess")
public class GameWebSocket {

	private final IUserService  userService  = new UserServiceImpl();

	@OnOpen
	public void open(Session session) {
	}

	@OnClose
	public void close(Session session) throws IOException {
		System.out.println("关闭了一个连接");
		Room room = RoomUtil.getRoom(session);
		if (room != null) {
			System.out.println("关闭了一个房间");
			GameMessage result = new GameMessage();
			result.setAction(301);
			GameUtil.sendMsg(session, result);
			Player anotherPlayer = room.getAnotherPlayer(session);
			if (anotherPlayer != null) {
				result.setAction(302);
				GameUtil.sendMsg(anotherPlayer.getSession(), result);
			}
			RoomUtil.removeRoom(room.getId());
		}
	}

	@OnError
	public void error(Session session, Throwable e) {
		System.out.println("发生错误！");
		e.printStackTrace();
	}

	@OnMessage
	public void message(Session session, String msg) throws IOException {
		GameMessage clientMsg = JSON.parseObject(msg, GameMessage.class);
		switch (clientMsg.getAction()) {
			case 1: {
				// 注册
				GameMessage result = new GameMessage();
				Room        room   = RoomUtil.getRoom(clientMsg.getRoom().getId());
				if (room == null) {
					result.setAction(200);
					result.setMsg("房间不存在");
					GameUtil.sendMsg(session, result);
					break;
				}
				room.initPlayer(clientMsg.getSender(), session);
				result.setRoom(room);
				result.setAction(100);
				GameUtil.sendMsg(session, result);

				if (room.getStatus() == 1) {
					// 游戏开始 互换信息
					result.setAction(300);
					Player play1 = room.getPlay1();
					Player play2 = room.getPlay2();

					result.setSender(play1);
					result.setMsg("white");
					GameUtil.sendMsg(play2.getSession(), result);

					result.setSender(play2);
					result.setMsg("black");
					GameUtil.sendMsg(play1.getSession(), result);
				}
				break;
			}
			case 2: {
				Room       room       = RoomUtil.getRoom(clientMsg.getRoom().getId());
				ChessBoard chessBoard = room.getChessBoard();

				if (chessBoard.playChess(clientMsg.getChess())) {
					if (chessBoard.isWin(clientMsg.getChess())) {
						clientMsg.setIsWin(1);
						userService.updateScore(room.getPlayer(session).getId(), 1000L, "123", 2);
						userService.updateScore(room.getAnotherPlayer(session).getId(), -1000L, "123", 2);
					}

					clientMsg.setAction(500);
					GameUtil.sendMsg(session, clientMsg);
					clientMsg.setAction(400);
					GameUtil.sendMsg(RoomUtil.getRoom(clientMsg.getRoom().getId())
									.getAnotherPlayer(session).getSession(),
							clientMsg);
				} else {
					clientMsg.setAction(501);
					GameUtil.sendMsg(session, clientMsg);
				}
			}
			break;
			default:
				break;
		}
	}


}
