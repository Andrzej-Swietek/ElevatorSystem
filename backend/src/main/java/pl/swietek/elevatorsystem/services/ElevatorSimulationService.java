package pl.swietek.elevatorsystem.services;

import org.springframework.stereotype.Service;
import pl.swietek.elevatorsystem.app.models.ElevatorStatus;

import java.util.List;
import java.util.stream.Stream;

public interface ElevatorSimulationService {
    void pickup(int floor, int direction);
    List<ElevatorStatus> status();
    void update(int elevatorID, int newCurrentFloor, int newCurrentDirection);
    void step();
    void reset();
}
