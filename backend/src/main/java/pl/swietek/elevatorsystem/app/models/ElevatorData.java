package pl.swietek.elevatorsystem.app.models;

import lombok.Data;

import java.util.Map;

public record ElevatorData(
        int currentFloor, int nextSelectedFloor,
        int currentOccupancy, ElevatorState elevatorState,
        Map<Integer, Integer> targetFloors,
        Map<Integer, Integer> pickupFloorsUp, Map<Integer, Integer> pickupFloorsDown) {
}
