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

## Stage 2 Message Handling
Start a debugging session.  
Use a JShell Console to encrypt and decrypt your message to go into taskData field
```
import java.util.Base64;
public class Utility {
    public final String decode(final String value) {
        final byte[] decodedBytes = Base64.getDecoder().decode(value);
        return new String(decodedBytes);
    }
    public final String encode(final String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}

Utility util = new Utility();
util.encode("{\"message\":\"Show me the code\"}");
util.decode("eyJtZXNzYWdlIjoiV2hhdCBQbGF5ZXIifQ==");
```
Pass in the base64 encrypted message {\"message\":\"Ready Player 1\"} to RabbitMQ worker
queue and review the logging output to see how the message is handled.


