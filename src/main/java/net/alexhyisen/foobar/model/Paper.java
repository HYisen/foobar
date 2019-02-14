package net.alexhyisen.foobar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
public class Paper {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private Long pid;
    private String title;
    private String content;

}
