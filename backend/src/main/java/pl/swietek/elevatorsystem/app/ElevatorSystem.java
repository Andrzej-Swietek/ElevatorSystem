package pl.swietek.elevatorsystem.app;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.swietek.elevatorsystem.app.models.Elevator;
import pl.swietek.elevatorsystem.app.models.ElevatorCall;
import pl.swietek.elevatorsystem.app.models.ElevatorState;
import pl.swietek.elevatorsystem.app.models.ElevatorStatus;

import java.util.*;
import java.util.stream.Stream;


@Data
public class ElevatorSystem implements Simulation, Iterable<Elevator> {

    private static final Logger log = LoggerFactory.getLogger(ElevatorSystem.class);
    private Long simulationId;
    private List<Elevator> elevators;
    public static int NO_FLOORS;
    public static int NO_ELEVATORS;

    private List<ElevatorCall> awaitingCalls = new ArrayList<>();

    public ElevatorSystem() {
        this(16,10);
    }

    public ElevatorSystem(int numberOfElevators, int numberOfFloors){
        NO_FLOORS = numberOfFloors;
        NO_ELEVATORS = numberOfElevators;
        this.elevators = new ArrayList<>(NO_ELEVATORS);
        for (int i = 0; i < NO_ELEVATORS; i++) {
            elevators.add(new Elevator(i, 0));
        }
    }

    @Override
    public synchronized void pickup(int floor, int direction) {
        List<Elevator> validElevators = this.elevators.stream()
                .filter( (
                        elevator -> elevator.getElevatorState().getValue() == direction && elevator.includesTargetFloor(floor)
                ))
                .filter((elevator -> elevator.getPrognosedOccupancy(floor) > 0))
                .toList();

        Elevator elevator = this.getOptimalElevator(validElevators, floor);

        if (elevator == null) {     // NO ELEVATOR FOR SUCH GUY THUS PUT HIS INTO WAIT LIST
            awaitingCalls.add(new ElevatorCall(floor, direction));
            return;
        }

        elevator.handlePickUp(floor, direction);
    }

    public synchronized void handleAwaitingCalls() {
        int removedCalls = 0;
        int originalWaitListLength = awaitingCalls.size();
        for (int i = 0; i < originalWaitListLength; i++) {
            ElevatorCall elevatorCall = awaitingCalls.remove(i-removedCalls++); //; TODO: ...
            this.pickup(elevatorCall.getStartingFloor(), elevatorCall.getDirection());
        }
    }

    @Override
    public List<ElevatorStatus> status() {
        return this.elevators.stream()
                .map(Elevator::getStatus)
                .toList();
    }

    @Override
    public void update(int elevatorID, int newCurrentFloor, int newTargetFloor) {
        Elevator elevator = elevators.stream()
                .filter(e -> e.getId() == elevatorID)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Elevator with id " + elevatorID + " not found"));

        elevator.update(newCurrentFloor, newTargetFloor);
    }

    @Override
    public void step() {
        // FIRST HANDLE ALREADY AWAITING CLIENTS
        this.handleAwaitingCalls();

        // HANDLE NORMAL CASE
        elevators.stream()
                .filter((Elevator e) -> e.getElevatorState() != ElevatorState.IDLE )
                .forEach(Elevator::step);
    }

    @Override
    public Iterator<Elevator> iterator() {
        return elevators.iterator();
    }

    private Elevator getOptimalElevator( List<Elevator> validElevators, int startingFloor ) {
        if (!validElevators.isEmpty()) {
            Optional<Elevator> optimalElevator = validElevators.stream()
                    .min(Comparator.comparingInt(
                            elevator -> elevator.getPrognosedTime(startingFloor))
                    );
            return optimalElevator.orElse(null);
        }

        // IDLE ELEVATORS - FILTER AND CHOOSE THE BEST
        return this.elevators.stream()
                .filter(elevator -> elevator.getElevatorState() == ElevatorState.IDLE)
                .min(new Elevator.ElevatorComparator(startingFloor))
                .orElse(null);

    }

    private List<Elevator> getIdleElevators() {
        return this.elevators.stream()
                .filter(elevator -> elevator.getElevatorState() == ElevatorState.IDLE)
                .sorted(Comparator.comparingInt(Elevator::getCurrentFloor))
                .toList();
    }
}
