package pl.swietek.elevatorsystem.app.models;

import java.util.List;

public record ElevatorStatus(int elevatorId, int currentFloor, int elevatorTargetFloors) {
}