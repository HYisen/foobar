package net.alexhyisen.foobar.module;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private Long uid;
    private String nickname;

    @Relationship(type = "FRIEND")
    private List<Person> friends=new ArrayList<>();
}
