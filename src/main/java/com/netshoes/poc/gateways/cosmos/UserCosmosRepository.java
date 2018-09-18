package com.netshoes.poc.gateways.cosmos;

import com.netshoes.poc.domains.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCosmosRepository extends MongoRepository<User, String> {
}
