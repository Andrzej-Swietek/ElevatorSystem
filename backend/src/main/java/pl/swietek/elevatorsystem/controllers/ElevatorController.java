package pl.swietek.elevatorsystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.swietek.elevatorsystem.app.models.ElevatorStatus;
import pl.swietek.elevatorsystem.services.ElevatorSimulationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elevator")
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
