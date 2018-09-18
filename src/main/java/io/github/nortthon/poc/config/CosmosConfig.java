package io.github.nortthon.poc.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.netshoes.poc.gateways.cosmos",
        mongoTemplateRef = "cosmosMongoTemplate"
)
public class CosmosConfig {

    @Value("${spring.data.cosmos.uri}")
    private String cosmosUri;

    @Bean
    public MongoDbFactory cosmosMongoDbFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClientURI(cosmosUri));
    }

    @Bean
    public MongoTemplate cosmosMongoTemplate(@Qualifier("cosmosMongoDbFactory") final MongoDbFactory cosmosMongoDbFactory) {
        return new MongoTemplate(cosmosMongoDbFactory);
    }
}
