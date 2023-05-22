package com.alva.game.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-21
 */public class ChessBoard {

	private Integer length   = 17;
	private Integer winCount = 5;

	private Integer[][] board     = new Integer[length][length];
	private List<Chess> chessList = new ArrayList<>(length * length);

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getWinCount() {
		return winCount;
	}

	public void setWinCount(Integer winCount) {
		this.winCount = winCount;
	}

	public Integer[][] getBoard() {
		return board;
	}

	public void setBoard(Integer[][] board) {
		this.board = board;
	}

	public List<Chess> getChessList() {
		return chessList;
	}

	public void setChessList(List<Chess> chessList) {
		this.chessList = chessList;
	}

	public boolean playChess(Chess chess) {
		if (board[chess.getPosX()][chess.getPosY()] != null) {
			return false;
		}
		board[chess.getPosX()][chess.getPosY()] = chess.getColorNum();
		chessList.add(chess);
		return true;
	}

	public boolean isWin(Chess chess) {
		int count = 1;
		int x     = chess.getPosX();
		int y     = chess.getPosY();
		while (x > 0 && chess.getColorNum().equals(board[--x][y])) {
			count++;
		}

		x = chess.getPosX();
		y = chess.getPosY();
		while (x < length - 1 && chess.getColorNum().equals(board[++x][y])) {
			count++;
		}
		if (count >= winCount) {
			return true;
		}

		count = 1;
		x = chess.getPosX();
		y = chess.getPosY();
		while (y > 0 && chess.getColorNum().equals(board[x][--y])) {
			count++;
		}

		x = chess.getPosX();
		y = chess.getPosY();
		while (y < length - 1 && chess.getColorNum().equals(board[x][++y])) {
			count++;
		}
		if (count >= winCount) {
			return true;
		}

		count = 1;
		x = chess.getPosX();
		y = chess.getPosY();
		while ((x > 0 && y > 0) && chess.getColorNum().equals(board[--x][--y])) {
			count++;
		}

		x = chess.getPosX();
		y = chess.getPosY();
		while ((x < length - 1 && y < length - 1) && chess.getColorNum().equals(board[++x][++y])) {
			count++;
		}
		if (count >= winCount) {
			return true;
		}

		count = 1;
		x = chess.getPosX();
		y = chess.getPosY();
		while ((x < length - 1 && y > 0) && chess.getColorNum().equals(board[++x][--y])) {
			count++;
		}

		x = chess.getPosX();
		y = chess.getPosY();
		while ((x > 0 && y < length - 1) && chess.getColorNum().equals(board[--x][++y])) {
			count++;
		}
		return count >= winCount;
	}

}
