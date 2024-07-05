package pl.swietek.elevatorsystem.app.models;

public enum ElevatorState {
    IDLE,
    UP,
    DOWN;

    public int getValue() {
        if(this == UP) return 1;
        else if(this == DOWN) return -1;
        return 0;
    }

    public static ElevatorState fromValue(int value) {
        return switch (value) {
            case 1 -> ElevatorState.UP;
            case -1 -> ElevatorState.DOWN;
            default -> ElevatorState.IDLE;
        };
    }
}
