package io.github.nortthon.poc.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import io.github.nortthon.poc.domains.SaveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static io.github.nortthon.poc.domains.CollectionType.getClazz;

@Slf4j
@Service
public class KafkaConsumer {

    private final MongoTemplate cosmosMongoTemplate;

    private final MappingMongoConverter mongoConverter;

    private final ObjectMapper om;

    public KafkaConsumer(@Qualifier("cosmosMongoTemplate") final MongoTemplate cosmosMongoTemplate,
                         final MappingMongoConverter mongoConverter, final ObjectMapper om) {
        this.cosmosMongoTemplate = cosmosMongoTemplate;
        this.mongoConverter = mongoConverter;
        this.om = om;
    }

    @KafkaListener(topics = "cosmos.topic.test")
    public void receive(@Payload SaveEvent saveEvent) throws IOException {
        log.info(saveEvent.getSource());

        final Object source = om.readValue(saveEvent.getSource(), getClazz(saveEvent.getCollectionName()));
        final DBObject dbObject = (DBObject) mongoConverter.convertToMongoType(source);

        cosmosMongoTemplate.getCollection(saveEvent.getCollectionName()).save(dbObject);
    }
}
