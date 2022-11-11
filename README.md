# WorkerTraining
Training project for the Worker Framework

Code to run a Worker in a docker container.

RabbitMQ can be monitored to post and monitor messages.

Demonstrates how the Stating point for the [Worker Framework](https://workerframework.github.io/worker-framework/pages/en-us/Architecture)
starts with the service defined under META-INF folder.  
This defines the in place Factory Provider class which allows the Factory to be created which is how the  
Worker Framework can create the worker objects for this project.