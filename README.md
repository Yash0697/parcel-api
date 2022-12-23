# parcel-api




###### H2 Database

The application uses **in memory** h2 database for storing rule configurations.
The advantage here is that we need not to connect to any physical database server.

###### Strategy Design Pattern
Implemented Strategy Design Pattern to invoke different rules to calculate cost 
based on configurations loaded from H2 database.

**Strategy** is a behavioral design pattern that can be used when different algorithms
or logic can be applied to solve a task and we can let client application to decide which
one to use. This involves declaring an interface with a method to execute the task.
Multiple *Strategies* can implement this interface and provide the different logic 
for the method. This way we can achieve loose coupling and write more scalable code.




