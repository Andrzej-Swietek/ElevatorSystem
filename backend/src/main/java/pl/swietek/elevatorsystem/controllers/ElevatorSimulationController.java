package pl.swietek.elevatorsystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import pl.swietek.elevatorsystem.app.models.ElevatorStatus;
import pl.swietek.elevatorsystem.requests.PickupRequest;
import pl.swietek.elevatorsystem.requests.UpdateRequest;
import pl.swietek.elevatorsystem.services.ElevatorSimulationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elevator-system")
public class ElevatorSimulationController {
    @Autowired
    private ElevatorSimulationService elevatorService;

    @PostMapping("/pickup")
    public ResponseEntity<Void> pickup(@RequestBody PickupRequest request) {
        request.validate();
        elevatorService.pickup(request.direction(), request.direction());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody UpdateRequest request) {
        request.validate();
        elevatorService.update(request.elevatorID(), request.newCurrentFloor(), request.newTargetFloor());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/step")
    public ResponseEntity<Void> step() {
        elevatorService.step();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<ElevatorStatus>> status() {
        return ResponseEntity.ok(elevatorService.status());
    }
}
