package net.alexhyisen.foobar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
public class Account {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String username;
    private String password;
}
