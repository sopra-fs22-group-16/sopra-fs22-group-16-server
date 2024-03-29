package ch.uzh.ifi.hase.soprafs22.rest.dto.get_dto;

import ch.uzh.ifi.hase.soprafs22.game.Position;
import ch.uzh.ifi.hase.soprafs22.game.units.enums.UnitCommands;
import ch.uzh.ifi.hase.soprafs22.game.units.enums.UnitType;

import java.util.List;

public class UnitGetDTO {
    private UnitType type;
    private int health;
    private int maxHealth;
    private List<Double> defense;
    private List<Double> attackDamage;
    private int attackRange;
    private int movementRange;
    private List<UnitCommands> commands;
    private int teamId;
    private long userId;
    private Position position;
    private boolean moved;

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public List<Double> getDefense() {
        return defense;
    }

    public void setDefense(List<Double> defense) {
        this.defense = defense;
    }

    public List<Double> getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(List<Double> attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getMovementRange() {
        return movementRange;
    }

    public void setMovementRange(int movementRange) {
        this.movementRange = movementRange;
    }

    public List<UnitCommands> getCommands() {
        return commands;
    }

    public void setCommands(List<UnitCommands> commands) {
        this.commands = commands;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean getMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
