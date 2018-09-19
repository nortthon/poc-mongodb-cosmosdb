package io.github.nortthon.poc.gateways.listeners;

import io.github.nortthon.poc.domains.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageEventListener {

    private final MongoTemplate cosmosMongoTemplate;

    public MessageEventListener(@Qualifier("cosmosMongoTemplate") final MongoTemplate cosmosMongoTemplate) {
        this.cosmosMongoTemplate = cosmosMongoTemplate;
    }

    @EventListener
    public void event(final UserEvent userEvent) {
        cosmosMongoTemplate.getCollection(userEvent.getCollection()).save(userEvent.getDbObject());
    }
}
