package io.github.nortthon.poc.gateways.cosmos;

import com.microsoft.azure.spring.data.documentdb.repository.DocumentDbRepository;
import io.github.nortthon.poc.domains.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCosmosRepository extends DocumentDbRepository<User, String> {
}
