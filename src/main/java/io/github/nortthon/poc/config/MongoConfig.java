package io.github.nortthon.poc.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongo.uri}")
    private String mongoUri;

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClientURI(mongoUri));
    }

    @Bean
    public MongoTemplate mongoTemplate(@Qualifier("mongoDbFactory") final MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }
}
