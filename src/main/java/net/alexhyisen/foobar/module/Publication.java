package net.alexhyisen.foobar.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "PUBLISH")
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

    public Long getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Person getPerson() {
        return person;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }
}
