package pl.swietek.elevatorsystem.requests;

import pl.swietek.elevatorsystem.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public record StartRequest(int numberOfElevators, int numberOfFloors) implements RequestData {

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (numberOfElevators <= 0) errors.add("Number of elevators must be greater than 0");
        if (numberOfFloors <= 0) errors.add("Number of floors must be greater than 0");
        if (numberOfElevators > 16) errors.add("Number of elevators must be less than 16");
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}
