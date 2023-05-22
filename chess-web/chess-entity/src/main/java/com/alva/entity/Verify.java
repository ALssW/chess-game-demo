package com.alva.entity;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-07
 */
public class Verify {

	private String code;
	private Long   seed;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getSeed() {
		return seed;
	}

	public void setSeed(Long seed) {
		this.seed = seed;
	}

	public Verify() {
	}

	public Verify(String code, Long seed) {
		this.code = code;
		this.seed = seed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Verify verify = (Verify) o;
		return this.code.equals(verify.getCode());
	}
}
