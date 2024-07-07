package pl.swietek.elevatorsystem.controllers;

import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import pl.swietek.elevatorsystem.app.models.ElevatorData;
import pl.swietek.elevatorsystem.app.models.ElevatorStatus;
import pl.swietek.elevatorsystem.requests.PickupRequest;
import pl.swietek.elevatorsystem.requests.StartRequest;
import pl.swietek.elevatorsystem.requests.UpdateRequest;
import pl.swietek.elevatorsystem.services.ElevatorSimulationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elevator-system")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ElevatorSimulationController {
    @Autowired
    private ElevatorSimulationService elevatorService;

    @PostMapping("/start")
    public ResponseEntity<?> start(@RequestBody StartRequest request) {
        request.validate();
        List<ElevatorData> elevatorsData = elevatorService.startSimulation(
                request.numberOfElevators(), request.numberOfFloors()
        );
        return  ResponseEntity.ok(elevatorsData);
    }

    @PostMapping("/pickup")
    public ResponseEntity<List<ElevatorData>> pickup(@RequestBody PickupRequest request) {
        request.validate();
        elevatorService.pickup(request.floor(), request.direction());
        return ResponseEntity.ok(elevatorService.getSimulationData());
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody UpdateRequest request) {
        request.validate();
        elevatorService.update(request.elevatorID(), request.newCurrentFloor(), request.newTargetFloor());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/step")
    public ResponseEntity<List<ElevatorData>> step() {
        elevatorService.step();
        return ResponseEntity.ok(elevatorService.getSimulationData());
    }

    @GetMapping("/status")
    public ResponseEntity<List<ElevatorStatus>> status() {
        return ResponseEntity.ok(elevatorService.status());
    }

    @GetMapping("/simulation-data")
    public ResponseEntity<List<ElevatorData>> getSimulationData() {
        return ResponseEntity.ok(elevatorService.getSimulationData());
    }



    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationFailedException(ValidationException exception) {
        return String.join("\n", exception.getMessage());
    }
}
