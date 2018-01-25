# Test Proces
* Debuggen
* In branches werken
* Pull requests
* JUnit



### Debuggen

System.out.println:
```Java
process
Car at (1.0,1.0) sent to exit after staying 164 updates.
Car parked at (1, 1) on floor 0
````

System.err.println:
```Java
Car at (1.0,1.0) sent to exit after staying 164 updates.
process
Car parked at (1, 1) on floor 0
````

<br><br>
Onze Logger.java:
```Java

INFO: process
com.parkingtycoon.helpers.Delegate.process(Delegate.java:42)
INFO: Car at (1.0,1.0) sent to exit after staying 164 updates.
com.parkingtycoon.controllers.FloorsController.update(FloorsController.java:49)
INFO: Car parked at (1, 1) on floor 0
com.parkingtycoon.controllers.FloorsController.parkCar(FloorsController.java:77)

````
