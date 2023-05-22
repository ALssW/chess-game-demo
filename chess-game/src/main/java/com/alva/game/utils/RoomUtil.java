package com.alva.game.utils;

import com.alva.game.entity.Room;
import com.alva.game.entity.RoomVO;

import javax.websocket.Session;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-20
 */
public class RoomUtil {

	private static final Map<Integer, Room> ROOM_MAP = new ConcurrentHashMap<>();

	public static void addRoom(Room room) {
		ROOM_MAP.put(room.getId(), room);
	}

	public static Room getRoom(Integer roomId) {
		return ROOM_MAP.get(roomId);
	}

	public static Room getRoom(Session session) {
		for (Room room : ROOM_MAP.values()) {
			if (room.getPlayer(session) != null){
				return room;
			}
		}
		return null;
	}

	public static void removeRoom(Integer roomId) {
		ROOM_MAP.remove(roomId);
	}

	public static List<RoomVO> getRooms() {
		return ROOM_MAP.values().stream().map(room -> {
			RoomVO roomVO = new RoomVO();
			roomVO.setId(room.getId());
			roomVO.setTitle(room.getTitle());
			roomVO.setOwner(room.getPlay1() != null ? room.getPlay1().getNickname() : "未知");
			roomVO.setHasPassword("".equals(room.getPassword()) || room.getPassword() == null  ? 0 : 1);
			roomVO.setStatus(room.getStatus());
			return roomVO;
		}).collect(Collectors.toList());
	}

}
