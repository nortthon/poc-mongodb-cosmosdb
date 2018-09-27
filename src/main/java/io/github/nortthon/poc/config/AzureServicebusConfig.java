package io.github.nortthon.poc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.servicebus.MessageHandlerOptions;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.ReceiveMode;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import io.github.nortthon.poc.listeners.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class AzureServicebusConfig {

    @Value("${azure.servicebus.connectionString}")
    private String connectionString;

    @Value("${azure.servicebus.queueName}")
    private String queueName;

    @Bean
    public QueueClient getQueueClient(@Qualifier("cosmosMongoTemplate") final MongoTemplate cosmosMongoTemplate,
                                      final MappingMongoConverter mongoConverter, final ObjectMapper om) throws ServiceBusException, InterruptedException {
        final MessageHandler messageHandler = new MessageHandler(cosmosMongoTemplate, mongoConverter, om);
        final MessageHandlerOptions messageHandlerOptions = new MessageHandlerOptions(1, true, Duration.ofMinutes(1));
        final ExecutorService executorService = Executors.newCachedThreadPool();

        final QueueClient queueClient = new QueueClient(new ConnectionStringBuilder(connectionString, queueName), ReceiveMode.PEEKLOCK);
        queueClient.registerMessageHandler(messageHandler, messageHandlerOptions, executorService);
        return queueClient;
    }
}
