package com.alva.game.entity;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-20
 */
public class GameMessage {

	/**
	 * 1-注册 2-落子
	 */
	private Integer action;
	private Chess   chess;
	private Room    room;
	private Player  sender;
	private String  msg;
	private Integer isWin;

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Chess getChess() {
		return chess;
	}

	public void setChess(Chess chess) {
		this.chess = chess;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Player getSender() {
		return sender;
	}

	public void setSender(Player sender) {
		this.sender = sender;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getIsWin() {
		return isWin;
	}

	public void setIsWin(Integer isWin) {
		this.isWin = isWin;
	}
}
