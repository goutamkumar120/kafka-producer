package com.goutam.service;

import com.goutam.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send("goutam-kafka-demo-4", message);
        future.whenComplete((result, ex) -> {
            if(ex == null) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" + message + "due to : " + ex.getMessage());
            }
        });
    }

    public void sendEventsToTopic(Customer customer) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("goutam-kafka-demo-5", customer);
            future.whenComplete((result, ex) -> {
                if(ex == null) {
                    System.out.println("Sent message=[" + customer + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" + customer + "due to : " + ex.getMessage());
                }
            });
        } catch (Exception ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }

    }
}

