package com.alva.game.entity;

import com.alibaba.fastjson2.annotation.JSONType;

import javax.websocket.Session;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-20
 */
@JSONType(ignores = "chessBoard")
public class Room {

	private static final AtomicInteger INCREMENT_ID = new AtomicInteger(0);

	private       Integer      id;
	private       String       title;
	private       String       password;
	private       Player       play1;
	private       Player       play2;
	private final List<Player> spectators = new CopyOnWriteArrayList<>();
	private       ChessBoard   chessBoard = new ChessBoard();
	/**
	 * 0: 未开始 1: 已开始
	 */
	private       Integer      status;

	public static AtomicInteger getIncrementId() {
		return INCREMENT_ID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Player getPlay1() {
		return play1;
	}

	public void setPlay1(Player play1) {
		this.play1 = play1;
	}

	public Player getPlay2() {
		return play2;
	}

	public void setPlay2(Player play2) {
		this.play2 = play2;
	}

	public List<Player> getSpectators() {
		return spectators;
	}

	public ChessBoard getChessBoard() {
		return chessBoard;
	}

	public void setChessBoard(ChessBoard chessBoard) {
		this.chessBoard = chessBoard;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Room() {
		setId(INCREMENT_ID.incrementAndGet());
	}

	public int addPlayer(Player player) {
		if (play1 == null || play1.getId().equals(player.getId())) {
			setPlay1(player);
			return 1;
		}

		synchronized (this) {
			if (this.getPlay2() == null) {
				this.setPlay2(player);
				//修改房间状态
				this.setStatus(1);
				return 2;
			}
		}
		return 3;
	}

	public void initPlayer(Player player, Session session) {
		initPlayer(player.getId(), player.getHeader(), player.getNickname(), session);
	}

	public void initPlayer(Integer uid, String header, String nickname, Session session) {
		if (play1 != null && play1.getId().equals(uid)) {
			setPlayer(play1, header, nickname, session);
		} else if (play2 != null && play2.getId().equals(uid)) {
			setPlayer(play2, header, nickname, session);
		}
	}

	private void setPlayer(Player player, String header, String nickname, Session session) {
		player.setNickname(nickname);
		player.setHeader(header);
		player.setSession(session);
	}

	public Player getPlayer(Session session) {
		if (play1 != null && session == play1.getSession()) {
			return play1;
		} else if (play2 != null && session == play2.getSession()) {
			return play2;
		}

		return null;
	}

	public Player getAnotherPlayer(Session session) {
		if (status != 1) {
			return null;
		}

		if (session == play1.getSession()) {
			return play2;
		} else if (session == play2.getSession()) {
			return play1;
		}

		return null;
	}
}
