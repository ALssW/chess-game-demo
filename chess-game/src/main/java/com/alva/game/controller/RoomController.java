package com.alva.game.controller;

import com.alva.annotaion.Dispatcher;
import com.alva.annotaion.RequestHandler;
import com.alva.annotaion.RequestParma;
import com.alva.dispatcher.entity.Response;
import com.alva.game.entity.Player;
import com.alva.game.entity.Room;
import com.alva.game.entity.RoomVO;
import com.alva.game.utils.RoomUtil;

import javax.servlet.annotation.WebServlet;
import java.util.List;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-20
 */
@Dispatcher
@WebServlet("/room")
public class RoomController {

	@RequestHandler("/create")
	public Response<Room> create(Room room, @RequestParma("userId") Integer userId) {
		Player player = new Player();
		player.setId(userId);

		room.addPlayer(player);
		room.setStatus(0);
		RoomUtil.addRoom(room);
		System.out.println("房间创建成功: " + room);
		return Response.ok("房间创建成功", room);
	}

	@RequestHandler("/list")
	public Response<List<RoomVO>> list() {
		return Response.ok(RoomUtil.getRooms());
	}

	@RequestHandler("/join")
	public Response<Room> join(@RequestParma("userId") Integer userId, @RequestParma("roomId") Integer roomId, @RequestParma("password") String password) {
		Room room = RoomUtil.getRoom(roomId);

		if (room == null){
			return Response.fail("房间已不存在");
		}

		if (room.getPassword() != null && !password.equals(room.getPassword())){
			return Response.fail("密码错误");
		}
		if (userId.equals(room.getPlay1().getId())){
			return Response.fail(301, "房主无法再次加入房间", null);
		}

		Player player = new Player();
		player.setId(userId);
		if (room.addPlayer(player) == 3){
			return Response.fail("房间已满");

		}
		return Response.ok(room);
	}

}
