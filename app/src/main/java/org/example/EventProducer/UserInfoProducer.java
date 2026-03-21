package org.example.EventProducer;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;

@Service
public class UserInfoProducer {
    

    private final KafkaTemplate<String, UserinfoEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Autowired
    UserInfoProducer(KafkaTemplate<String, UserinfoEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEventToKafka(UserinfoEvent userinfoDto){
        Message<UserinfoEvent> message = MessageBuilder.withPayload(userinfoDto)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
        kafkaTemplate.send(message);
    }


}