package pl.swietek.elevatorsystem.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Direction {
    UP,
    DOWN;

    public int getValue() {
        if(this == UP) return 1;
        return -1;
    }

}
