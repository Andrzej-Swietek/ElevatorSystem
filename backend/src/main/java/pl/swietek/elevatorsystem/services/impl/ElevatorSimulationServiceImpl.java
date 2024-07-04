package pl.swietek.elevatorsystem.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.swietek.elevatorsystem.app.ElevatorSystem;
import pl.swietek.elevatorsystem.app.models.ElevatorStatus;
import pl.swietek.elevatorsystem.services.ElevatorSimulationService;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class ElevatorSimulationServiceImpl implements ElevatorSimulationService {

    private ElevatorSystem elevatorSystem;

    ElevatorSimulationServiceImpl(){
        this.elevatorSystem = new ElevatorSystem();
    }


    @Override
    public void pickup(int floor, int direction) {
        this.elevatorSystem.pickup(floor, direction);
    }

    @Override
    public List<ElevatorStatus> status() {
        return List.of();
    }

    @Override
    public void update(int elevatorID, int newCurrentFloor, int newTargetFloor) {
        this.elevatorSystem.update(elevatorID, newCurrentFloor, newTargetFloor);
    }

    @Override
    public void step() {
        this.elevatorSystem.step();
    }

    @Override
    public void reset() {

    }
}
