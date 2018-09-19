package io.github.nortthon.poc.listeners;

import io.github.nortthon.poc.domains.DeleteEvent;
import io.github.nortthon.poc.domains.SaveEvent;
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
    public void saveEvent(final SaveEvent saveEvent) {
        cosmosMongoTemplate.getCollection(saveEvent.getCollection()).save(saveEvent.getDbObject());
    }

    @EventListener
    public void deleteEvent(final DeleteEvent deleteEvent) {
        cosmosMongoTemplate.getCollection(deleteEvent.getCollection()).remove(deleteEvent.getDbObject());
    }
}
