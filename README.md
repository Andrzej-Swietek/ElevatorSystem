# Elevators System
<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=git,docker,java,spring" />
  </a>
</p>
<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=angular,typescript,threejs,tailwind" />
  </a>
</p>

## About
Elevators System is a simulation project that models the behavior and operation of elevators within a building. The system is designed to handle multiple elevators, manage requests efficiently, and ensure that the elevators operate in an optimal manner. The project includes both backend and frontend components.

## Strategy
The strategy for elevator selection and travel is based on several principles to optimize efficiency and minimize wait times:
1. <b>Nearest Car Algorithm:</b> The elevator that is closest to the request floor is selected.
2. <b>Load Balancing:</b> If multiple elevators are equally close, the system selects the one with the least load.
3. <b>Directional Logic:</b> Elevators already moving towards the request direction are prioritized.
4. <b>Idle Elevators:</b> Idle elevators are moved towards floors with the highest traffic based on historical data.
   Priority Handling: Requests from higher priority floors (like lobby or top floors) are given precedence.

## Running 

### Docker
To run the application using Docker, you can use the following command:
```shell
docker-compose up --build -d 
```

| Service      | Listening Port         |
|--------------|------------------------|
| Frontend     | http://localhost:3000/ |
| Backend      | http://localhost:8000/ |
| Nginx (http) | http://localhost:8080/ |


### Locally
To set up and run the frontend locally:
- Frontend
1. Install the dependencies:
```shell
npm i
```
2. Running in development mode:

```shell
ng serve
```

3. Running in production mode:

```shell
ng build
```

App will be listening at: `http://localhost:4200/`
___

- Backend
  To set up and run the backend locally:

1. Compilation
``` 
mvn clean package
```
This shall create `target` directory containing the `.jar` file.

``` 
java -jar [path to file].jar
```
example:
``` 
java -jar target/elevatorsystem-0.0.1-SNAPSHOT.jar
```

App will be listening at: `http://localhost:8080/`


## Swagger
Backend Swagger documentation for the API can be accessed at:
```
http://localhost:8000/swagger-ui/index.html#/
```


## Unit Tests

Unit tests are provided only for backend since the whole business logic is implemented right there.

To run unit tests, use the following commands:
```shell
mvn test
```

## Usage Manual

### 1. In order to start interactions with elevator system press `Start Simulation` button


![image](https://github.com/Andrzej-Swietek/ElevatorSystem/blob/22ddd419ecc43d563c1e573e750171fb2dafa652/docs/home.png?raw=true)

### 2. Subsequently, enter simulation data such us number of floors in simulated building as well as number of elevators
![image](https://github.com/Andrzej-Swietek/ElevatorSystem/blob/22ddd419ecc43d563c1e573e750171fb2dafa652/docs/start-simulation.png?raw=true)

### 3. Window of simulation
![image](https://github.com/Andrzej-Swietek/ElevatorSystem/blob/22ddd419ecc43d563c1e573e750171fb2dafa652/docs/simulation-1-1.jpg?raw=true)

- Refresh Button - Reloads data from server and updates UI (Manually)
  
    In case introduced new source of input for simulation
- Step Button - Make the next step in the simulation
- Reset Button - Resets whole simulation

### 4. Display elevator Details by clicking on the selected elevator
![image](https://github.com/Andrzej-Swietek/ElevatorSystem/blob/22ddd419ecc43d563c1e573e750171fb2dafa652/docs/simulation-modal.png?raw=true)
