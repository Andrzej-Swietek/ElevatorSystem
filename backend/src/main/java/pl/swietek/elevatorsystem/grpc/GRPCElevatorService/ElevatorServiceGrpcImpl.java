package pl.swietek.elevatorsystem.grpc.GRPCElevatorService;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import pl.swietek.elevatorsystem.grpc.*;
import pl.swietek.elevatorsystem.services.ElevatorSimulationService;
import pl.swietek.elevatorsystem.grpc.ElevatorServiceGrpc;
//import pl.swietek.elevatorsystem.app.models.ElevatorState as ModelElevatorState;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class ElevatorServiceGrpcImpl extends ElevatorServiceGrpc.ElevatorServiceImplBase {

    private final ElevatorSimulationService elevatorSimulationService;

    @Autowired
    public ElevatorServiceGrpcImpl(ElevatorSimulationService elevatorSimulationService) {
        this.elevatorSimulationService = elevatorSimulationService;
    }

    @Override
    public void pickup(PickupRequest request, StreamObserver<PickupResponse> responseObserver) {
        elevatorSimulationService.pickup(request.getFloor(), request.getDirection());
        PickupResponse response = PickupResponse.newBuilder()
                .addAllElevatorsData(convertToGrpcElevatorData(elevatorSimulationService.getSimulationData()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void status(StatusRequest request, StreamObserver<StatusResponse> responseObserver) {
        List<pl.swietek.elevatorsystem.app.models.ElevatorStatus> statuses = elevatorSimulationService.status();
        StatusResponse response = StatusResponse.newBuilder()
                .addAllElevatorStatuses(statuses.stream()
                        .map(this::convertToGrpcElevatorStatus)
                        .collect(Collectors.toList()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void update(UpdateRequest request, StreamObserver<UpdateResponse> responseObserver) {
        elevatorSimulationService.update(request.getElevatorId(), request.getNewCurrentFloor(), request.getNewTargetFloor());
        List<pl.swietek.elevatorsystem.app.models.ElevatorData> data = elevatorSimulationService.getSimulationData();
        UpdateResponse response = UpdateResponse.newBuilder()
                .addAllElevatorsData(convertToGrpcElevatorData(data))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void step(StepRequest request, StreamObserver<StepResponse> responseObserver) {
        elevatorSimulationService.step();
        List<pl.swietek.elevatorsystem.app.models.ElevatorData> data = elevatorSimulationService.getSimulationData();
        StepResponse response = StepResponse.newBuilder()
                .addAllElevatorsData(convertToGrpcElevatorData(data))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void reset(ResetRequest request, StreamObserver<ResetResponse> responseObserver) {
        List<pl.swietek.elevatorsystem.app.models.ElevatorData> data = elevatorSimulationService.startSimulation(request.getNumberOfElevators(), request.getNumberOfFloors());
        ResetResponse response = ResetResponse.newBuilder()
                .addAllElevatorsData(convertToGrpcElevatorData(data))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void startSimulation(StartSimulationRequest request, StreamObserver<StartSimulationResponse> responseObserver) {
        List<pl.swietek.elevatorsystem.app.models.ElevatorData> data = elevatorSimulationService.startSimulation(request.getNumberOfElevators(), request.getNumberOfFloors());
        StartSimulationResponse response = StartSimulationResponse.newBuilder()
                .addAllElevatorsData(convertToGrpcElevatorData(data))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getSimulationData(GetSimulationDataRequest request, StreamObserver<GetSimulationDataResponse> responseObserver) {
        List<pl.swietek.elevatorsystem.app.models.ElevatorData> data = elevatorSimulationService.getSimulationData();
        GetSimulationDataResponse response = GetSimulationDataResponse.newBuilder()
                .addAllElevatorsData(convertToGrpcElevatorData(data))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getElevator(GetElevatorRequest request, StreamObserver<GetElevatorResponse> responseObserver) {
        pl.swietek.elevatorsystem.app.models.ElevatorData data = elevatorSimulationService.getElevator(request.getId());
        GetElevatorResponse response = GetElevatorResponse.newBuilder()
                .setElevatorData(convertToGrpcElevatorData(data))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Iterable<? extends ElevatorData> convertToGrpcElevatorData(List<pl.swietek.elevatorsystem.app.models.ElevatorData> dataList) {
        return dataList.stream()
                .map(this::convertToGrpcElevatorData)
                .collect(Collectors.toList());
    }

    private ElevatorData convertToGrpcElevatorData(pl.swietek.elevatorsystem.app.models.ElevatorData data) {
        return ElevatorData.newBuilder()
                .setCurrentFloor(data.currentFloor())
                .setNextSelectedFloor(data.nextSelectedFloor())
                .setCurrentOccupancy(data.currentOccupancy())
                .setElevatorState(ElevatorState.valueOf(data.elevatorState().name()))

                .build();
    }

    private ElevatorStatus convertToGrpcElevatorStatus(pl.swietek.elevatorsystem.app.models.ElevatorStatus status) {
        return ElevatorStatus.newBuilder()
                .setElevatorId(status.elevatorId())
                .setCurrentFloor(status.currentFloor())
                .setTargetFloor(status.elevatorTargetFloors())
                .build();
    }
}
