package pl.swietek.elevatorsystem.requests;

import pl.swietek.elevatorsystem.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public record PickupRequest(int floor, int direction)
        implements RequestData {

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (floor <= 0) errors.add("floor must be greater than 0");
        if (direction <= 0) errors.add("direction must be greater than 0");
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}
