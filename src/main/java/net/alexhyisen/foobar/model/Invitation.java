package net.alexhyisen.foobar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "INVITE")
@Data
public class Invitation {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;
    private long timestamp;

    @StartNode
    private Person src;
    @EndNode
    private Person dst;
}
