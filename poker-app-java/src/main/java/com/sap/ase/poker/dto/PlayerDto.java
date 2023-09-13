package com.sap.ase.poker.dto;

public class PlayerDto {

    private String id;
    private String name;
    private int cash;

    public PlayerDto() {
    }

    public PlayerDto(com.sap.ase.poker.model.Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.cash = player.getCash();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
