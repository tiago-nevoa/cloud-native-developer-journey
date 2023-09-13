package com.sap.ase.poker.dto;

public class BetRequestDto {

	private int[] args;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int[] getArgs() {
		return args;
	}

	public void setArgs(int[] args) {
		this.args = args;
	}
}
