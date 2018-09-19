package io.github.nortthon.poc.config;

import com.mongodb.BasicDBObject;
import io.github.nortthon.poc.domains.DeleteEvent;
import io.github.nortthon.poc.domains.SaveEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoEventListener extends AbstractMongoEventListener<Object> {

    private final ApplicationEventPublisher publisher;

    @Override
    public void onAfterSave(AfterSaveEvent<Object> event) {
        super.onAfterSave(event);
        publisher.publishEvent(new SaveEvent(event, event.getCollectionName(), (BasicDBObject) event.getDBObject()));
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Object> event) {
        super.onAfterDelete(event);
        publisher.publishEvent(new DeleteEvent(event, event.getCollectionName(), (BasicDBObject) event.getDBObject()));
    }

}
