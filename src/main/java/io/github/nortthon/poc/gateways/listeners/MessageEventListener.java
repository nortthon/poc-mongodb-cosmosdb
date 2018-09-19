package io.github.nortthon.poc.gateways.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nortthon.poc.domains.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MessageEventListener {

    private final MongoTemplate cosmosMongoTemplate;

    private final ObjectMapper om;

    public MessageEventListener(@Qualifier("cosmosMongoTemplate") final MongoTemplate cosmosMongoTemplate, final ObjectMapper om) {
        this.cosmosMongoTemplate = cosmosMongoTemplate;
        this.om = om;
    }

    @EventListener
    public void event(final UserEvent userEvent) throws IOException {
        cosmosMongoTemplate.getCollection(userEvent.getCollection()).save(userEvent.getDbObject());
    }
}
