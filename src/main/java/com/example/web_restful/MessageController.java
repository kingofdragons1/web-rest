package com.example.web_restful;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {
    LocalDateTime dateTime1 = LocalDateTime.parse("2014-04-08 12:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    LocalDateTime dateTime2 = LocalDateTime.parse("2022-05-20 15:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    LocalDateTime dateTime3 = LocalDateTime.parse("2023-09-10 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    private List<Message> messages = new ArrayList<>(Arrays.asList(
            new Message(1, "Упавший прод", "Александр, добрый день, вы опять уронили прод", dateTime1),
            new Message(2, "Unit-тесты", "Александр, вы не написали Unit-тесты, теперь вы курьер Яндекс доставки", dateTime2),
            new Message (3, "Хорошийй оффер", "Александр, мы рады предложить вам работу в Microsoft", dateTime3)
    ));

    @GetMapping("/message")
    public Iterable<Message> getMessage() {
        return messages;
    }

    @GetMapping("/message/{id}")
    public Optional<Message> findMessageById(@PathVariable int id) {
        return messages.stream().filter(p -> p.getId() == id).findFirst();
    }

    @PostMapping("/message")
    public Message addMessage(@RequestBody Message message) {
        messages.add(message);
        return message;
    }


    @PutMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message message) {
        int index = - 1;
        for (Message p : messages) {
            if (p.getId() == id) {
                index = messages.indexOf(p);
                messages.set(index, message);
            }
        }
        return index == -1
                ? new ResponseEntity<>(addMessage(message), HttpStatus.CREATED)
                : new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/message/{id}")
    public void deletePerson(@PathVariable int id) {
        messages.removeIf(p -> p.getId() == id);
    }

}



