package com.netshoes.poc.gateways;

import com.netshoes.poc.domains.User;
import com.netshoes.poc.gateways.cosmos.UserCosmosRepository;
import com.netshoes.poc.gateways.mongo.UserMongoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.util.Arrays.asList;

@Component
public class UserGateway {

    private final UserMongoRepository mongoRepository;

    private final UserCosmosRepository cosmosRepository;

    public UserGateway(UserMongoRepository mongoRepository, UserCosmosRepository cosmosRepository) {
        this.mongoRepository = mongoRepository;
        this.cosmosRepository = cosmosRepository;
    }

    public Optional<User> findById(final String id) {
        final Random rand = new Random();
        final List<MongoRepository<User, String>> repositories = asList(mongoRepository, cosmosRepository);
        final MongoRepository<User, String> repository = repositories.get(rand.nextInt(2));

        return Optional.ofNullable(repository.findOne(id));
    }

    public List<User> findAll() {
        final Random rand = new Random();
        final List<MongoRepository<User, String>> repositories = asList(mongoRepository, cosmosRepository);
        final MongoRepository<User, String> repository = repositories.get(rand.nextInt(2));

        return repository.findAll();
    }

    public User save(final User user) {
        final User savedUser = mongoRepository.save(user);
        cosmosRepository.save(savedUser);
        return savedUser;
    }

    public void delete(final String id) {
        mongoRepository.delete(id);
        cosmosRepository.delete(id);
    }
}
