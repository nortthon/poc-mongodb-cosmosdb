package io.github.nortthon.poc.config;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.spring.data.documentdb.config.AbstractDocumentDbConfiguration;
import com.microsoft.azure.spring.data.documentdb.repository.config.EnableDocumentDbRepositories;
import io.github.nortthon.poc.gateways.cosmos.UserCosmosRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDocumentDbRepositories(basePackageClasses = UserCosmosRepository.class)
public class CosmosConfig extends AbstractDocumentDbConfiguration {

    @Value("${azure.documentdb.uri}")
    private String uri;

    @Value("${azure.documentdb.key:}")
    private String key;

    @Value("${azure.documentdb.database}")
    private String dbName;

    @Override
    public DocumentClient documentClient() {
        return new DocumentClient(uri, key, ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);
    }

    @Override
    public String getDatabase() {
        return dbName;
    }
}
