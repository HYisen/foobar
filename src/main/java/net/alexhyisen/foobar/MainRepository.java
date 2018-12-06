package net.alexhyisen.foobar;

import net.alexhyisen.foobar.module.Person;
import net.alexhyisen.foobar.module.Publication;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MainRepository extends Neo4jRepository<Person, Long> {
    @Query("MATCH (a:Person {uid:{uid}})-[r:PUBLISH]->(p) RETURN a,r,p ORDER BY r.timestamp DESC SKIP {s} LIMIT {l};")
    Collection<Publication> findPublications(@Param("uid") Long uid, @Param("s") Long skip, @Param("l") Long limit);

    @Query("MATCH (a:Person {uid:{uid}})-[:FRIEND]-(b:Person) RETURN b ORDER BY b.uid SKIP {s} LIMIT {l};")
    Collection<Person> findFriends(@Param("uid") Long uid, @Param("s") Long skip, @Param("l") Long limit);

    @Query("MATCH (a:Person {uid:{uid}})-[:FRIEND*0..1]-(b)-[r:PUBLISH]->(p) RETURN b,r,p" +
            " ORDER BY r.timestamp DESC SKIP {s} LIMIT {l};")
    Collection<Publication> findMoments(@Param("uid") Long uid, @Param("s") Long skip, @Param("l") Long limit);

    @Query("MATCH (a:Person {uid:{uid}})-[:FRIEND]-(b:Person)-[:FRIEND]-(c:Person)" +
            " WHERE NOT (a)-[:FRIEND]-(c) RETURN c ORDER BY c.uid SKIP {s} LIMIT {l};")
    Collection<Person> findStrangers(@Param("uid") Long uid, @Param("s") Long skip, @Param("l") Long limit);

    @Query("MATCH (a:Person {uid:{srcUid}})-[:FRIEND]-(b:Person)-[:FRIEND]-(c:Person {uid:{dstUid}})" +
            " RETURN DISTINCT b ORDER BY b.uid SKIP {s} LIMIT {l};")
    Collection<Person> findAgents(@Param("srcUid") Long srcUid, @Param("dstUid") Long dstUid,
                                  @Param("s") Long skip, @Param("l") Long limit);

//    @Query("MATCH (a:Person {uid:{pub.person.uid}})" +
//            " CREATE (a)-[:PUBLISH {timestamp:{pub.timestamp}}]->" +
//            "(p:Paper {pid:{pub.paper.pid},title:{pub.paper.title},content:{pub.paper.content}})")
//    Publication addPaper(@Param("pub") Publication publication);

    @Query("MATCH (a:Person {uid:{uid}})" +
            " CREATE (a)-[r:PUBLISH {timestamp:{timestamp}}]->(p:Paper {pid:{pid},title:{title},content:{content}})" +
            " RETURN a,r,p;")
    Publication addPaper(
            @Param("uid") long uid,
            @Param("pid") long pid,
            @Param("timestamp") long timestamp,
            @Param("title") String title,
            @Param("content") String content
    );


    @Query("MATCH (a:Person {uid:{uid}})-[:PUBLISH]->(p:Paper {pid:{pid}}) DETACH DELETE p;")
    void delPaper(@Param("uid") long uid, @Param("pid") long pid);
}
