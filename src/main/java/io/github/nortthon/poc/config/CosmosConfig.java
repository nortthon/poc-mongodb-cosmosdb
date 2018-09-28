package io.github.nortthon.poc.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

@Configuration
public class CosmosConfig {

    @Value("${spring.data.cosmos.uri}")
    private String cosmosUri;

    @Bean
    public MongoTemplate cosmosMongoTemplate() throws UnknownHostException {
        return new MongoTemplate(new SimpleMongoDbFactory(new MongoClientURI(cosmosUri)));
    }
}
