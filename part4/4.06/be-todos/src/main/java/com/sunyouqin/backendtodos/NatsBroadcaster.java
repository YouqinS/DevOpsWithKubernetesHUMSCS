package com.sunyouqin.backendtodos;

import io.nats.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.IOException;

@org.springframework.stereotype.Service
public class NatsBroadcaster {

    @Autowired
    private Environment env;

    @Value("${NATS_URL:jnats://localhost:4222}")
    private String serverURI;//="nats://localhost:4222";

    private Connection natsConnection;

    private final static Logger log = LoggerFactory.getLogger(NatsBroadcaster.class);

    @PostConstruct
    public void init() {
        natsConnection = initConnection(serverURI);
    }

    private Connection initConnection(String uri) {
        System.out.println("Connecting to " + uri);

        try {
            Options options = new Options.Builder()
                    .errorCb(ex -> log.error("Connection Exception: ", ex))
                    .disconnectedCb(event -> log.error("Channel disconnected: {}", event.getConnection()))
                    .reconnectedCb(event -> log.error("Reconnected to server: {}", event.getConnection()))
                    .build();

            return Nats.connect(uri, options);
        } catch (IOException ioe) {
            log.error("Error connecting to NATs! ", ioe);
            ioe.printStackTrace();
            return null;
        }
    }

    public void publishMessage(String topic, String replyTo, String message) {
        try {
            natsConnection.publish(topic, replyTo, message.getBytes());
        } catch (IOException ioe) {
            log.error("Error publishing message: {} to {} ", message, topic, ioe);
        }
    }
}