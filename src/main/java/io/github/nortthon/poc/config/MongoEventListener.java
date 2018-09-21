package io.github.nortthon.poc.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nortthon.poc.domains.ColdBase;
import io.github.nortthon.poc.domains.SaveEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoEventListener extends AbstractMongoEventListener<Object> {

    private final KafkaTemplate<String, SaveEvent> kafkaTemplate;

    private final ObjectMapper om;

    @Override
    public void onAfterSave(AfterSaveEvent<Object> event) {
        super.onAfterSave(event);

        if (event.getSource().getClass().isAnnotationPresent(ColdBase.class)) {
            final String source = serialize(event.getSource());
            kafkaTemplate.send("cosmos.topic.test", new SaveEvent(event.getCollectionName(), source));
        }
    }

    private String serialize(final Object source) {
        try {
            return om.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
