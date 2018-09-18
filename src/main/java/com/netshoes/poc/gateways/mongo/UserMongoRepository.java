package com.netshoes.poc.gateways.mongo;

import com.netshoes.poc.domains.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {
}
