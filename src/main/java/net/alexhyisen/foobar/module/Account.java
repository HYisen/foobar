package net.alexhyisen.foobar.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
class Account {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String username;
    private String password;
}
