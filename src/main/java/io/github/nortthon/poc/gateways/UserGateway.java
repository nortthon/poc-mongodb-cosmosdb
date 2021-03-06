package io.github.nortthon.poc.gateways;

import io.github.nortthon.poc.domains.User;
import io.github.nortthon.poc.gateways.cosmos.UserCosmosRepository;
import io.github.nortthon.poc.gateways.mongo.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class UserGateway {

    private final UserMongoRepository mongoRepository;

    private final UserCosmosRepository cosmosRepository;

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
        return mongoRepository.save(user);
    }

    public void delete(final String id) {
        mongoRepository.delete(id);
    }
}
