package pl.swietek.elevatorsystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.swietek.elevatorsystem.app.models.ElevatorStatus;
import pl.swietek.elevatorsystem.services.ElevatorSimulationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elevator")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ElevatorController {
    @Autowired
    private ElevatorSimulationService elevatorService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllElevators() {
        return ResponseEntity.ok(this.elevatorService.getSimulationData());
    }

    @GetMapping("/all-statuses")
    public ResponseEntity<List<ElevatorStatus>> getAllElevatorsStatuses() {
        return ResponseEntity.ok(this.elevatorService.status());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getElevatorById(@PathVariable Long id) {
        return ResponseEntity.ok(this.elevatorService.getElevator(id));
    }

}
