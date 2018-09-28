package io.github.nortthon.poc.gateways;

import io.github.nortthon.poc.domains.User;
import io.github.nortthon.poc.gateways.mongo.UserMongoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserGateway {

    private final UserMongoRepository mongoRepository;

    private final MongoTemplate cosmosMongoTemplate;

    public UserGateway(UserMongoRepository mongoRepository, @Qualifier("cosmosMongoTemplate") MongoTemplate cosmosMongoTemplate) {
        this.mongoRepository = mongoRepository;
        this.cosmosMongoTemplate = cosmosMongoTemplate;
    }

    public Optional<User> findById(final String id) {
        //return Optional.ofNullable(mongoRepository.findOne(id));
        return Optional.ofNullable(cosmosMongoTemplate.findById(id, User.class));
    }

    public List<User> findAll() {
        //return mongoRepository.findAll();
        return cosmosMongoTemplate.findAll(User.class);
    }

    public User save(final User user) {
        return mongoRepository.save(user);
    }

    public void delete(final String id) {
        mongoRepository.delete(id);
    }
}
