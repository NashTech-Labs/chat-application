# chat-application
This project provide a sample application to send messages to various users using akka cluster sharding concept

Commands to run node 1:

export NETTY_PORT=2551
export HTTP_PORT=8081
sbt "project messaging-service" run

Commands to run node 2:

export NETTY_PORT=2552
export HTTP_PORT=8082
sbt "project messaging-service" run
