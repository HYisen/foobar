package net.alexhyisen.foobar.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "LINK")
@Data
public class Link {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @StartNode
    private Account account;
    @EndNode
    private Person person;
}
