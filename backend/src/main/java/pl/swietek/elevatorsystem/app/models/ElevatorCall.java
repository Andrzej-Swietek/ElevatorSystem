package pl.swietek.elevatorsystem.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElevatorCall {
    private int startingFloor;
    private int direction;
}
