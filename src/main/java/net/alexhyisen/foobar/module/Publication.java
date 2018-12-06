package net.alexhyisen.foobar.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "PUBLISH")
@Data
public class Publication {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private Long timestamp;

    @StartNode
    private Person person;
    @EndNode
    private Paper paper;
}
