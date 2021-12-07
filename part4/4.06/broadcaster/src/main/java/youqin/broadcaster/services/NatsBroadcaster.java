package youqin.broadcaster.services;

import io.nats.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
@org.springframework.stereotype.Service
public class NatsBroadcaster {

    @Autowired
    private Environment env;

    @Value("${NATS_URL:jnats://localhost:4222}")
    private String serverURI;

    @Value("${BOT_TOKEN:}")
    private String BOT_TOKEN;

    @Value("${CHAT_ID:}")
    private String CHAT_ID;

    private Connection natsConnection;

    private final Map<String, Subscription> subscriptions = new HashMap<>();

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

    public void subscribeAsync(String topic, MessageHandler handler) {
        System.out.println("Subscribing to topic:" + topic);
        AsyncSubscription subscription = natsConnection.subscribe(topic, handler);

        if (subscription == null) {
            log.error("Error subscribing to {}", topic);
        } else {
            subscriptions.put(topic, subscription);
        }
    }

    public void subscribeAsync(String topic, String queueGroup, MessageHandler handler) {
        System.out.println("Subscribing to topic: " + topic);
        System.out.println("Subscribing to queueGroup: " + queueGroup);
        AsyncSubscription subscription = natsConnection.subscribe(topic, queueGroup, handler);

        if (subscription == null) {
            log.error("Error subscribing to {}", topic);
        } else {
            subscriptions.put(topic, subscription);
        }
    }

    public void sendToTelegramBot(String message) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/" + "bot" + BOT_TOKEN + "/sendMessage")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("text", message);


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + BOT_TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();


        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}