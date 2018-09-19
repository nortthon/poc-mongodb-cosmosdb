package io.github.nortthon.poc.domains;

import com.mongodb.BasicDBObject;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DeleteEvent extends ApplicationEvent {

    private final String collection;

    private final BasicDBObject dbObject;

    public DeleteEvent(final Object source, final String collection, final BasicDBObject dbObject) {
        super(source);
        this.collection = collection;
        this.dbObject = dbObject;
    }
}
