package net.alexhyisen.foobar.module;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MainRepository extends Neo4jRepository<Person, Long> {
    @Query("MATCH (a:Person {uid:{uid}})-[r:PUBLISH]->(p) RETURN a,r,p ORDER BY r.timestamp DESC SKIP {s} LIMIT {l};")
    Collection<Publication> findPublications(@Param("uid") Long uid, @Param("s") Long skip, @Param("l") Long limit);

    @Query("MATCH (a:Person {uid:{uid}})-[:FRIEND]-(b:Person) RETURN b ORDER BY b.uid SKIP {s} LIMIT {l};")
    Collection<Person> findFriends(@Param("uid") Long uid, @Param("s") Long skip, @Param("l") Long limit);
}
