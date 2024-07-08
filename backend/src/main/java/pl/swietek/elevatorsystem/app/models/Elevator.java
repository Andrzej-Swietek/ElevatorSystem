package pl.swietek.elevatorsystem.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.swietek.elevatorsystem.app.ElevatorSystem;
import pl.swietek.elevatorsystem.app.RandomFloorGenerator;

import java.util.*;

@Data
public class Elevator {
    public final int id;
    private final static int MAX_OCCUPANCY = 15;

    private int currentFloor = 0;
    private int nextSelectedFloor; // Very last target it has on its list

    private int currentOccupancy = 0;
    private ElevatorState elevatorState = ElevatorState.IDLE;

    private Map<Integer, Integer> targetFloors = new HashMap<>();
    private Map<Integer, Integer> pickupFloorsDown = new HashMap<>();
    private Map<Integer, Integer> pickupFloorsUp = new HashMap<>();

    private final RandomFloorGenerator randomFloorGenerator = new RandomFloorGenerator();


    public Elevator(int id, int currentFloor){
        this.id = id;
        this.currentFloor = currentFloor;
    }


    public ElevatorStatus getStatus(){
        return new ElevatorStatus(id, currentFloor, nextSelectedFloor);
    }

    public void setElevatorState(ElevatorState elevatorState){ this.elevatorState = elevatorState; }


    public void update(int newCurrentFloor, int newTargetFloor) {
        this.currentFloor = newCurrentFloor;

        //this.nextSelectedFloor = newCurrentDirection;

        this.incrementOrSetCount(targetFloors, newTargetFloor);
    }

    public void handlePickUp(int startingFloor, int direction ) {
        // TODO: CHECK SELECTED FLOOR
        if (elevatorState == ElevatorState.IDLE)
            elevatorState = ElevatorState.fromValue(Integer.compare(startingFloor, currentFloor));

        this.incrementOrSetCount(direction > 0 ? pickupFloorsUp : pickupFloorsDown, startingFloor);  // COMING TO PICK UP A CLIENT
    }

    public void step(){
        // If anyone leaving remove them from elevator
        if (targetFloors.containsKey(currentFloor)) {
            currentOccupancy -= targetFloors.get(currentFloor);
            targetFloors.remove(currentFloor);

            if (currentOccupancy <= 0) elevatorState= ElevatorState.IDLE;
            return;
        }
        if (pickupFloorsUp.containsKey(currentFloor)) {
            currentOccupancy += pickupFloorsUp.get(currentFloor);
            this.setElevatorState(ElevatorState.UP);

            // CLIENTS CHOOSE THE FLOORS
            for (int i = 0; i < pickupFloorsUp.get(currentFloor); i++) {
                final int chosenFloor = randomFloorGenerator.getNextFloor(currentFloor+1, ElevatorSystem.NO_FLOORS-1);
                this.incrementOrSetCount(targetFloors, chosenFloor);
            }
            pickupFloorsUp.remove(currentFloor);
        }
        if (pickupFloorsDown.containsKey(currentFloor)) {
            currentOccupancy += pickupFloorsDown.get(currentFloor);
            this.setElevatorState(ElevatorState.DOWN);

            // CLIENTS CHOOSE THE FLOORS
            for (int i = 0; i < pickupFloorsDown.get(currentFloor); i++) {
                final int chosenFloor = randomFloorGenerator.getNextFloor(0, currentFloor);
                this.incrementOrSetCount(targetFloors, chosenFloor);
            }
            pickupFloorsDown.remove(currentFloor);
        }
        // Else or when they already left go up
        nextSelectedFloor = this.calculateNextSelectedFloor();


        if ( currentFloor == nextSelectedFloor && targetFloors.isEmpty()) {
            elevatorState = ElevatorState.IDLE; // STOP LIFT
        } else {
            currentFloor += elevatorState.getValue();
        }

        // SAFETY REDUNDANCY
        if (currentFloor < 0) {
            currentFloor = 0; elevatorState = ElevatorState.IDLE;
        }

        if (currentFloor > ElevatorSystem.NO_FLOORS) {
            currentFloor = ElevatorSystem.NO_FLOORS; elevatorState = ElevatorState.IDLE;
        }
    }

    public ElevatorData getElevatorData(){
        return new ElevatorData(currentFloor, nextSelectedFloor, currentOccupancy, elevatorState, targetFloors, pickupFloorsUp, pickupFloorsDown );
    }

    private int calculateNextSelectedFloor() {
        Set<Integer> combinedTargets = new HashSet<>();
        combinedTargets.addAll(pickupFloorsUp.keySet());
        combinedTargets.addAll(pickupFloorsDown.keySet());
        combinedTargets.addAll(targetFloors.keySet());
        return switch (elevatorState) {
            case IDLE -> combinedTargets.stream().min(Comparator.naturalOrder()).orElse(0);
            case UP -> combinedTargets.stream().max(Comparator.naturalOrder()).orElse(0);
            case DOWN -> combinedTargets.stream().min(Comparator.naturalOrder()).orElse(0);
        };
    }

    public boolean includesTargetFloor(int targetFloor){
        if (ElevatorState.DOWN.equals(elevatorState) && currentFloor >= targetFloor) return true;
        return ElevatorState.UP.equals(elevatorState) && currentFloor <= targetFloor;
    }

    public Set<Integer> getCombinedPickupFloors() {
        Set<Integer> combinedPickup = new HashSet<>();
        combinedPickup.addAll(pickupFloorsUp.keySet());
        combinedPickup.addAll(pickupFloorsDown.keySet());

        return combinedPickup;
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

        int numberOfNewPassengersUp = this.pickupFloorsUp.keySet().stream()
                .filter(targetFloor -> {
                    if (elevatorState == ElevatorState.UP) {
                        return targetFloor >= currentFloor && targetFloor <= floor;
                    } else {
                        return targetFloor <= currentFloor && targetFloor >= floor;
                    }
                })
                .reduce( 0, (acc, key) -> this.pickupFloorsUp.get(key) != null ? acc + this.pickupFloorsUp.get(key) : acc);

        int numberOfNewPassengersDown = this.pickupFloorsUp.keySet().stream()
                .filter(targetFloor -> {
                    if (elevatorState == ElevatorState.UP) {
                        return targetFloor >= currentFloor && targetFloor <= floor;
                    } else {
                        return targetFloor <= currentFloor && targetFloor >= floor;
                    }
                })
                .reduce( 0, (acc, key) -> this.pickupFloorsDown.get(key) != null ? acc + this.pickupFloorsDown.get(key) : acc + 0);

        return currentOccupancy - numberOfLeavingPassengers + numberOfNewPassengersUp + numberOfNewPassengersDown;
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


    private void incrementOrSetCount(Map<Integer,Integer> map, int selectedFloor){
        map.computeIfPresent(selectedFloor, (key, value) -> value + 1);
        if( !map.containsKey(selectedFloor) ) map.put(selectedFloor, 1);
    }

}


