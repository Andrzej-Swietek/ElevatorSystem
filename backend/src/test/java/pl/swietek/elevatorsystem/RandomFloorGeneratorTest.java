package pl.swietek.elevatorsystem;

import org.junit.jupiter.api.Test;
import pl.swietek.elevatorsystem.app.RandomFloorGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomFloorGeneratorTest {
    @Test
    public void testGetNextFloor() {
        RandomFloorGenerator generator = new RandomFloorGenerator();
        int floor = generator.getNextFloor();
        assertTrue(floor >= 0);
    }

    @Test
    public void testGetNextFloorInRange() {
        RandomFloorGenerator generator = new RandomFloorGenerator();
        int from = 5;
        int to = 10;
        int floor = generator.getNextFloor(from, to);
        assertTrue(floor >= from && floor <= to);
    }


    @Test
    public void testGetNextFloorInRangeMultiple() {
        RandomFloorGenerator generator = new RandomFloorGenerator();
        for (int i = 0; i < 10; i++) {
            int from = 0;
            int to = 5;
            int floor = generator.getNextFloor(from, to);
            System.out.println(from + " " + to + " " + floor + "\n");
            assertTrue(floor >= from && floor <= to);
        }


    }
}
