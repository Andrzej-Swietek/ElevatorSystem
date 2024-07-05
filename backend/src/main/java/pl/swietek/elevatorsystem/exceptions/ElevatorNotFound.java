package pl.swietek.elevatorsystem.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ElevatorNotFound extends RuntimeException {
    private final int id;

    @Override
    public String getMessage() {
        return "Elevator with id " + id + " not found";
    }
}
