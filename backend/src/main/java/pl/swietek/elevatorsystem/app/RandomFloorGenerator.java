package pl.swietek.elevatorsystem.app;

import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomFloorGenerator {

    private static final long MULTIPLIER = 16807L;
    private static final long MODULUS = 2147483647L;
    private long seed = 404635L;

    public int getNextFloor() {
        seed = (MULTIPLIER * seed) % MODULUS;
        return (int) seed;
    }

    public int getNextFloor(int from, int to) {
        seed = (MULTIPLIER * seed) % MODULUS;
        long randomNumber = seed;

        long range = (long) to - (long) from + 1;

        randomNumber = Math.abs(randomNumber % range) + from;

//        return (int) randomNumber;
        Random random = new Random();
        return random.nextInt((to - from) + 1) + from;
    }
}
