package io.github.nortthon.poc.gateways.cosmos;

import io.github.nortthon.poc.domains.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCosmosRepository extends MongoRepository<User, String> {
}
