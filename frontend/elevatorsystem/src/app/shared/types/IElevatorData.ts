export interface IElevatorData {
  currentFloor: number;
  nextSelectedFloor: number;
  currentOccupancy: number;
  elevatorState: ElevatorState;
  targetFloors: Record<number, number>;
  pickupFloorsUp: Record<number, number>;
  pickupFloorsDown: Record<number, number>;
}

export enum ElevatorState {
  IDLE,
  UP=1,
  DOWN=-1
}
