package pl.swietek.elevatorsystem.app.models;

import lombok.Data;
import pl.swietek.elevatorsystem.app.ElevatorSystem;
import pl.swietek.elevatorsystem.app.RandomFloorGenerator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Data
public class Elevator {
    public final int id;
    private final static int MAX_OCCUPANCY = 15;

    private int currentFloor = 0;
    private int nextSelectedFloor; //TODO: ?????
    private int currentOccupancy = 0;
    private ElevatorState elevatorState = ElevatorState.IDLE;

    private Map<Integer, Integer> targetFloors = new HashMap<>();

    private final RandomFloorGenerator randomFloorGenerator = new RandomFloorGenerator();

    public ElevatorStatus getStatus(){
        return new ElevatorStatus(id, currentFloor, nextSelectedFloor);
    }

    public void setElevatorState(ElevatorState elevatorState){ this.elevatorState = elevatorState; }


    public void update(int newCurrentFloor, int newCurrentDirection) {
        this.currentFloor = newCurrentFloor;
        this.nextSelectedFloor = newCurrentDirection;
    }

    public void handlePickUp(int startingFloor, int direction ) {
        // TODO: CHECK SELECTED FLOOR
        currentOccupancy++;
        if (elevatorState == ElevatorState.IDLE)
            elevatorState = ElevatorState.fromValue(direction);

        final int chosenFloor = switch(elevatorState) {
            case IDLE -> randomFloorGenerator.getNextFloor(0, ElevatorSystem.NO_FLOORS);
            case DOWN -> randomFloorGenerator.getNextFloor(0, startingFloor);
            case UP  -> randomFloorGenerator.getNextFloor(startingFloor, ElevatorSystem.NO_FLOORS);
        };

        targetFloors
                .computeIfPresent(chosenFloor, (key, value) -> value + 1);

        if( !targetFloors.containsKey(chosenFloor) ) targetFloors.put(chosenFloor, 1);
    }

    public void step(){
        // If anyone leaving remove them from elevator
        if (targetFloors.containsKey(currentFloor)) {
            currentOccupancy -= targetFloors.get(currentFloor);
            targetFloors.remove(currentFloor);

            if (currentOccupancy <= 0) elevatorState= ElevatorState.IDLE;
            return;
        }
        // Else or when they already left go up
        currentFloor += elevatorState.getValue();
    }

    public boolean includesTargetFloor(int targetFloor){
        if (ElevatorState.DOWN.equals(elevatorState) && currentFloor >= targetFloor) return true;
        return ElevatorState.UP.equals(elevatorState) && currentFloor <= targetFloor;
    }

    public void getNewPassenger() {

    }

    public int getPrognosedOccupancy(int floor) {
        int numberOfLeavingPassengers = this.targetFloors.keySet().stream()
                .filter(targetFloor -> {
                    if (elevatorState == ElevatorState.UP) {
                        return targetFloor >= currentFloor && targetFloor <= floor;
                    } else {
                        return targetFloor <= currentFloor && targetFloor >= floor;
                    }
                })
                .reduce( 0, (acc, key) -> acc + this.targetFloors.get(key));


        return currentOccupancy - numberOfLeavingPassengers;
    }

    /**
     * Method giving prediction on what's the cost of traversing to pick up
     * @param floor
     * @return How much time/steps does it take to reach given floor
     */
    public int getPrognosedTime(int floor) {
        int numberOfStops = this.targetFloors.keySet().stream()
                .filter(targetFloor -> {
                    if (elevatorState == ElevatorState.UP) {
                        return targetFloor >= currentFloor && targetFloor <= floor;
                    } else {
                        return targetFloor <= currentFloor && targetFloor >= floor;
                    }
                }).toList().size();

        return Math.abs(currentFloor - floor) + numberOfStops;
    }


    public static class ElevatorComparator implements Comparator<Elevator> {
        private final int startingFloor;

        public ElevatorComparator(int startingFloor) {
            this.startingFloor = startingFloor;
        }

        @Override
        public int compare(Elevator e1, Elevator e2) {
            // Compare by distance to startingFloor
            int distance1 = Math.abs(e1.getCurrentFloor() - startingFloor);
            int distance2 = Math.abs(e2.getCurrentFloor() - startingFloor);

            if (distance1 != distance2) {
                return Integer.compare(distance1, distance2);
            }

            // If distances are equal, prefer the elevator above the starting floor
            boolean e1Above = e1.getCurrentFloor() >= startingFloor;
            boolean e2Above = e2.getCurrentFloor() >= startingFloor;

            if (e1Above && !e2Above) {
                return -1;
            } else if (!e1Above && e2Above) {
                return 1;
            }

            return 0;
        }
    }

    private int getLastFloor() {
        if( elevatorState == ElevatorState.UP) return targetFloors.keySet()
                .stream()
                .max(Comparator.naturalOrder())
                .orElse(ElevatorSystem.NO_FLOORS);
        else return targetFloors.keySet().stream()
                .min(Comparator.naturalOrder())
                .orElse(0);
    }

}
