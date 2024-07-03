package pl.swietek.elevatorsystem.app;

import lombok.Data;
import pl.swietek.elevatorsystem.app.models.Elevator;
import pl.swietek.elevatorsystem.app.models.ElevatorState;
import pl.swietek.elevatorsystem.app.models.ElevatorStatus;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Data
public class ElevatorSystem implements Simulation, Iterable<Elevator> {

    private Long simulationId;
    private List<Elevator> elevators;
    public static final int NO_FLOORS = 10;

    @Override
    public synchronized void pickup(int floor, int direction) {
        // TODO: LOCK vs Synchronized
        List<Elevator> validElevators = this.elevators.stream()
                .filter( (
                        elevator -> elevator.getElevatorState().getValue() == direction && elevator.includesTargetFloor(floor)
                ))
                .filter((elevator -> elevator.getPrognosedOccupancy(floor) > 0))
                .toList();

        Elevator elevator = this.getOptimalElevator(validElevators, floor);
        if (elevator == null) {
            // TODO: ...

            return;
        }
        elevator.handlePickUp(floor, direction);

    }

    @Override
    public List<Stream<ElevatorStatus>> status() {
        return List.of(
                this.elevators.stream().map(Elevator::getStatus)
        );
    }

    @Override
    public void update(int elevatorID, int newCurrentFloor, int newCurrentDirection) {
        Elevator elevator = elevators.stream()
                .filter(e -> e.getId() == elevatorID)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Elevator with id " + elevatorID + " not found"));

        elevator.update(newCurrentFloor, newCurrentDirection);
    }

    @Override
    public void step() {
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
