package io.github.nortthon.poc.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
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
    public void receive(@Payload String payload, @Header("collection-name") String collectionName) throws IOException {
        log.info("Collection name: {} with payload: {}", collectionName, payload);

        final Object source = om.readValue(payload, getClazz(collectionName));
        final DBObject dbObject = (DBObject) mongoConverter.convertToMongoType(source);

        cosmosMongoTemplate.getCollection(collectionName).save(dbObject);
    }
}
