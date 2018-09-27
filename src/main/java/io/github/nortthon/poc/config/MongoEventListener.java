package io.github.nortthon.poc.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.QueueClient;
import io.github.nortthon.poc.domains.ColdBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
public class MongoEventListener extends AbstractMongoEventListener<Object> {

    private final QueueClient queueClient;

    private final ObjectMapper om;

    public MongoEventListener(final QueueClient queueClient, final ObjectMapper om) {
        this.queueClient = queueClient;
        this.om = om;
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Object> event) {
        super.onAfterSave(event);

        if (event.getSource().getClass().isAnnotationPresent(ColdBase.class)) {
            final String source = serialize(event.getSource());
            queueClient.sendAsync(new Message(source.getBytes(UTF_8)));
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
