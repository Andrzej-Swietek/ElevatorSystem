package pl.swietek.elevatorsystem;

import org.junit.jupiter.api.Test;
import pl.swietek.elevatorsystem.app.ElevatorSystem;
import pl.swietek.elevatorsystem.app.models.Elevator;
import pl.swietek.elevatorsystem.app.models.ElevatorStatus;

import java.util.List;

public class ElevatorSystemTest {
    @Test
    public void testStart() {
        ElevatorSystem elevatorSystem = new ElevatorSystem();
        elevatorSystem.pickup(0, 1); // ELEVATOR 0
        elevatorSystem.step();
        elevatorSystem.pickup(1, 1); // ELEVATOR 0
        elevatorSystem.step();

        List<ElevatorStatus> result0 = elevatorSystem.status();
        System.out.println(result0);

        elevatorSystem.pickup(2, -1); // ELEVATOR 1
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        elevatorSystem.step();
        List<ElevatorStatus> result = elevatorSystem.status();
        System.out.println(result);
    }

    @Test
    public void testSingleElevator() {
        ElevatorSystem elevatorSystem = new ElevatorSystem(1,10);
        elevatorSystem.pickup(2, 1);

        elevatorSystem.step();
        for( Elevator elevator : elevatorSystem )
            System.out.println(elevator.getElevatorData());


        elevatorSystem.step();
        for( Elevator elevator : elevatorSystem )
            System.out.println(elevator.getElevatorData());


        elevatorSystem.step();
        for( Elevator elevator : elevatorSystem )
            System.out.println(elevator.getElevatorData());

        elevatorSystem.step();
        for( Elevator elevator : elevatorSystem )
            System.out.println(elevator.getElevatorData());
    }

    @Test
    public void testAwaitElevator() {
        ElevatorSystem elevatorSystem = new ElevatorSystem(1,5);
        elevatorSystem.pickup(1, 1);
        elevatorSystem.step();
        elevatorSystem.step();
        for( Elevator elevator : elevatorSystem )
            System.out.println(elevator.getElevatorData());

        final int STEPS = 10;

        elevatorSystem.pickup(0, 1);
        for(int i =0; i < STEPS; i++) {
            System.out.println("\n==================[ " + i + " ]==================\n");
            elevatorSystem.step();

            for( Elevator elevator : elevatorSystem )
                System.out.println(elevator.getElevatorData());
        }


    }

    @Test
    public void testSingleManyPickupsElevator() {
        ElevatorSystem elevatorSystem = new ElevatorSystem(1,10);
        elevatorSystem.pickup(2, 1);
        elevatorSystem.pickup(2, 1);

        elevatorSystem.step();
        for( Elevator elevator : elevatorSystem )
            System.out.println(elevator.getElevatorData());


        elevatorSystem.step();
        for( Elevator elevator : elevatorSystem )
            System.out.println(elevator.getElevatorData());


        elevatorSystem.step();
        for( Elevator elevator : elevatorSystem )
            System.out.println(elevator.getElevatorData());

        elevatorSystem.step();
        for( Elevator elevator : elevatorSystem )
            System.out.println(elevator.getElevatorData());
    }


    @Test
    public void testAllElevatorsBusy() {
        ElevatorSystem elevatorSystem = new ElevatorSystem(2, 10);
        elevatorSystem.pickup(0, 1);
        elevatorSystem.pickup(5, 1);
        elevatorSystem.pickup(9, -1);

        for (int i = 0; i < 3; i++) {
            elevatorSystem.step();
        }

        elevatorSystem.pickup(2, 1);
        elevatorSystem.pickup(7, -1);

        for (int i = 0; i < 10; i++) {
            elevatorSystem.step();
        }

        List<ElevatorStatus> statuses = elevatorSystem.status();
        for (ElevatorStatus status : statuses) {
            System.out.println(status);
        }
    }


    @Test
    public void testMultipleElevatorsDifferentDirections() {
        ElevatorSystem elevatorSystem = new ElevatorSystem(3, 10);
        elevatorSystem.pickup(0, 1); // ELEVATOR 0
        elevatorSystem.pickup(5, -1); // ELEVATOR 1
        elevatorSystem.pickup(9, -1); // ELEVATOR 2

        for (int i = 0; i < 5; i++) {
            elevatorSystem.step();
        }

        List<ElevatorStatus> statuses = elevatorSystem.status();
        for (ElevatorStatus status : statuses) {
            System.out.println(status);
        }
    }


    @Test
    public void testSameFloorPickup() {
        ElevatorSystem elevatorSystem = new ElevatorSystem(2, 10);
        elevatorSystem.pickup(3, 1);
        elevatorSystem.pickup(3, -1);

        for (int i = 0; i < 7; i++) {
            System.out.println("\n==================[ " + i + " ]==================\n");

            elevatorSystem.step();
            for( Elevator elevator : elevatorSystem )
                System.out.println(elevator.getElevatorData());


        }

//        for( Elevator elevator : elevatorSystem )
//            System.out.println(elevator.getElevatorData());
    }


    @Test
    public void testMaximumLoadElevators() {
        ElevatorSystem elevatorSystem = new ElevatorSystem(5, 10);
        for (int i = 0; i < 10; i++) {
            elevatorSystem.pickup(i, 1);
        }

        for (int i = 0; i < 15; i++) {
            elevatorSystem.step();
        }

        List<ElevatorStatus> statuses = elevatorSystem.status();
        for (ElevatorStatus status : statuses) {
            System.out.println(status);
        }
    }

    @Test
    public void testAllElevatorRunning() {
        ElevatorSystem elevatorSystem = new ElevatorSystem(16, 10);
    }
}


