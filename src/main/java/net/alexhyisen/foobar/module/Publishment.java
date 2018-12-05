package net.alexhyisen.foobar.module;

import org.neo4j.ogm.annotation.*;

import java.time.LocalDateTime;

@RelationshipEntity(type = "PUBLISH")
public class Publishment {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime timestamp;

    @StartNode
    private Person person;
    @EndNode
    private Paper paper;
}
