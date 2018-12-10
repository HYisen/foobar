package net.alexhyisen.foobar.module;

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

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
}
