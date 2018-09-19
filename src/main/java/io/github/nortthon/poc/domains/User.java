package io.github.nortthon.poc.domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ColdBase
@Getter
@Setter
@Document
public class User {

    @Id
    private String id;

    private String name;

    private String mail;
}
