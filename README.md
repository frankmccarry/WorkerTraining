# WorkerTraining
Training project for the Worker Framework

Code to run a Worker in a docker container.

RabbitMQ can be monitored to post and monitor messages.

Demonstrates how the Stating point for the [Worker Framework](https://workerframework.github.io/worker-framework/pages/en-us/Architecture)
starts with the service defined under META-INF folder.  
This defines the in place Factory Provider class which allows the Factory to be created which is how the  
Worker Framework can create the worker objects for this project.

## Stage 1 Debugging
Start a running environment to test and attach a debugger to `mvn -Puse-default-fixed-ports docker:start` 
Attach a remote debugger allowing breakpoints to be set and see impact of putting a message on Rabbit MQ
via the rabbit MQ UI `http://localhost:<port in profile>/#/queues` 

Starter message 
``` 
{
	"version": 1,
	"taskId": "1.1",
	"taskClassifier": "DocumentWorker",
	"taskApiVersion": 1,
	"taskData": "A Message",
	"taskStatus": "RESULT_SUCCESS",
	"context": {},
	"to": "worker-starter-in",
	"tracking": null,
	"sourceInfo": {
		"name": "worker-starter",
		"version": "1.0.0"
	}
}
``` 