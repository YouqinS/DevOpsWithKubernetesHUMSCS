package com.sunyouqin.backendtodos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommandLineAppStartupRunner implements ApplicationRunner {
    @Autowired
    private TodoRepository repository;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("creating reminder");
        var reminder = new Todo();
        ResponseEntity<String> response = restTemplate.getForEntity("https://en.wikipedia.org/wiki/Special:Random", String.class);
        var message = "Read "+response.getHeaders().get("Location").get(0);
        System.out.println("  reminder message:"+message);
        reminder.setContent(message);
        reminder.setStatus("todo");
        repository.save(reminder);

        initiateShutdown();
    }

    private void initiateShutdown(){
        SpringApplication.exit(appContext, () -> 0); //0 no error
    }
}