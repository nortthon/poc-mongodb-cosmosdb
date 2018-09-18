package io.github.nortthon.poc.gateways.mongo;

import io.github.nortthon.poc.domains.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {
}
