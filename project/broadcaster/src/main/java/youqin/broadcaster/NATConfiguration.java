package youqin.broadcaster;

import io.nats.client.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import youqin.broadcaster.services.NatsBroadcaster;

import java.io.IOException;

@Configuration
public class NATConfiguration {

    @Bean
    public Object listener(NatsBroadcaster broadcaster) {
        System.out.println("listening to nat messages:");
        MessageHandler handler = msg -> {
            System.out.println("Received message:" + msg);
            try {
                broadcaster.sendToTelegramBot(new String(msg.getData()));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        };

        broadcaster.subscribeAsync("test","broadcasterGroup", handler);

        return new Object();
    }
}
