package net.alexhyisen.foobar.repository;

import net.alexhyisen.foobar.model.Account;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends Neo4jRepository<Account, Long> {
    @Query("MATCH (a:Account{username:{username}}) RETURN count(a)<>0;")
    boolean existsByUsername(@Param("username") String username);
}
