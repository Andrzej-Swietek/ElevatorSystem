package pl.swietek.elevatorsystem.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ValidationException extends RuntimeException  {
    private List<String> errors;
}
