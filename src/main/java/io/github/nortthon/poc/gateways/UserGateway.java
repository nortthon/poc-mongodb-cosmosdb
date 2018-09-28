package io.github.nortthon.poc.gateways;

import io.github.nortthon.poc.domains.User;
import io.github.nortthon.poc.gateways.cosmos.UserCosmosRepository;
import io.github.nortthon.poc.gateways.mongo.UserMongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserGateway {

    private final UserCosmosRepository cosmosRepository;

    private final UserMongoRepository mongoRepository;

    public UserGateway(UserCosmosRepository cosmosRepository, UserMongoRepository mongoRepository) {
        this.cosmosRepository = cosmosRepository;
        this.mongoRepository = mongoRepository;
    }


    public Optional<User> findById(final String id) {
        return Optional.ofNullable(mongoRepository.findOne(id));
    }

    public List<User> findAll() {
        return mongoRepository.findAll();
    }

    public User save(final User user) {
        return mongoRepository.save(user);
    }

    public void delete(final String id) {
        mongoRepository.delete(id);
    }
}
