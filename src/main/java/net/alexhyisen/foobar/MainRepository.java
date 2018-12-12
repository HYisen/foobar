package net.alexhyisen.foobar;

import net.alexhyisen.foobar.module.Invitation;
import net.alexhyisen.foobar.module.Link;
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

    @Query("MATCH (p:Paper) RETURN p.pid AS num ORDER BY num DESC LIMIT 1;")
    long findMaxPid();

    @Query("CREATE (a:Account {username:{username},password:{password}})-[l:LINK]->" +
            "(p:Person {uid:{uid},nickname:{nickname}}) RETURN a,l,p;")
    Link addUser(
            @Param("username") String username,
            @Param("password") String password,
            @Param("uid") long uid,
            @Param("nickname") String nickname
    );

    @Query("MATCH (p:Person) RETURN p.uid AS num ORDER BY num DESC LIMIT 1;")
    long findMaxUid();

    @Query("MATCH (src:Person {uid: {srcUid}}) MATCH (dst:Person {uid: {dstUid}})" +
            " WHERE NOT ((src)-[:FRIEND]-(dst) OR (src)-[:INVITE]-(dst))\n" +
            "CREATE (src)-[l:INVITE {timestamp: {timestamp}, message: {message}}]->(dst)" +
            "RETURN src,l,dst")
    Invitation createInvitation(
            @Param("srcUid") long srcUid,
            @Param("dstUid") long dstUid,
            @Param("timestamp") long timestamp,
            @Param("message") String message
    );

    @Query("MATCH (src:Person {uid: {srcUid}})-[r:INVITE]->(dst:Person {uid: {dstUid}}) DELETE r")
    void deleteInvitation(
            @Param("srcUid") long srcUid,
            @Param("dstUid") long dstUid
    );

    @Query("MATCH (src:Person)-[r:INVITE]->(dst:Person {uid: {uid}}) RETURN src,r,dst " +
            "ORDER BY r.timestamp DESC SKIP {s} LIMIT {l};")
    Collection<Invitation> findImportInvitations(@Param("uid") long uid, @Param("s") Long skip, @Param("l") Long limit);

    @Query("MATCH (src:Person {uid: {uid}})-[r:INVITE]->(dst:Person) RETURN src,r,dst " +
            "ORDER BY r.timestamp DESC SKIP {s} LIMIT {l};")
    Collection<Invitation> findExportInvitations(@Param("uid") long uid, @Param("s") Long skip, @Param("l") Long limit);
}
