package pl.swietek.elevatorsystem.requests;

import pl.swietek.elevatorsystem.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public record UpdateRequest(int elevatorID, int newCurrentFloor, int newCurrentDirection)
        implements RequestData {

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (elevatorID <= 0) errors.add("elevatorID must be greater than 0");
        if (newCurrentFloor <= 0) errors.add("newCurrentFloor must be greater than 0");
        if (newCurrentDirection <= 0) errors.add("newCurrentDirection must be greater than 0");
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}
