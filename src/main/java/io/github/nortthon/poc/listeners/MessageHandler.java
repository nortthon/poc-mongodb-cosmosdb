package io.github.nortthon.poc.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.servicebus.ExceptionPhase;
import com.microsoft.azure.servicebus.IMessage;
import com.microsoft.azure.servicebus.IMessageHandler;
import com.mongodb.DBObject;
import io.github.nortthon.poc.domains.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
public class MessageHandler implements IMessageHandler {

    private final MongoTemplate cosmosMongoTemplate;

    private final MappingMongoConverter mongoConverter;

    private final ObjectMapper om;

    public MessageHandler(@Qualifier("cosmosMongoTemplate") final MongoTemplate cosmosMongoTemplate,
                          final MappingMongoConverter mongoConverter, final ObjectMapper om) {
        this.cosmosMongoTemplate = cosmosMongoTemplate;
        this.mongoConverter = mongoConverter;
        this.om = om;
    }

    @Override
    public CompletableFuture<Void> onMessageAsync(IMessage message) {
        final String content = new String(message.getBody(), UTF_8);

        log.info(content);
        try {
            final User source = om.readValue(content, User.class);
            final DBObject dbObject = (DBObject) mongoConverter.convertToMongoType(source);
            cosmosMongoTemplate.getCollection("user").save(dbObject);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void notifyException(Throwable throwable, ExceptionPhase exceptionPhase) {
        log.error("{} - {}", exceptionPhase.toString(), throwable.getMessage());
    }
}
