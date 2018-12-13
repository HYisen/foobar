package net.alexhyisen.foobar.security;

import net.alexhyisen.foobar.module.Link;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface LinkRepository extends Neo4jRepository<Link, Long> {
    Link findByAccount_Username(String username);
}
