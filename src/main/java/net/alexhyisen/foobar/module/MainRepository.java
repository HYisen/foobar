package net.alexhyisen.foobar.module;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainRepository extends Neo4jRepository<Person, Long> {
    @Query("MATCH (a:Person {uid:{uid}})-[FRIEND]-(b)-[r:PUBLISH]->(p) RETURN r.timestamp,p ORDER BY r.timestamp DESC")
    List<Paper> findFriendsPapers(@Param("uid") Long uid);
}
