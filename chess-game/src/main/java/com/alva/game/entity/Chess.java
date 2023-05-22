package com.alva.game.entity;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-20
 */
public class Chess {

	private Integer posX;
	private Integer posY;
	private Integer num;
	private String color;
	private Integer colorNum;

	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getColorNum() {
		return colorNum;
	}

	public void setColorNum(Integer colorNum) {
		this.colorNum = colorNum;
	}
}
