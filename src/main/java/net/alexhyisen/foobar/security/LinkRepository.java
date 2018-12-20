package net.alexhyisen.foobar.security;

import net.alexhyisen.foobar.module.Link;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface LinkRepository extends Neo4jRepository<Link, Long> {
    Link findByAccount_Username(String username);

    @Query("MATCH (a:Account)-[r:LINK]->(p:Person {uid:{uid}}) SET a.password={pwd} RETURN a,r,p")
    Link updatePasswordByUid(@Param("uid") long uid, @Param("pwd") String password);
}
