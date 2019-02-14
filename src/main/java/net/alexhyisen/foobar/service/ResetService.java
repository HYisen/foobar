package net.alexhyisen.foobar.service;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class ResetService {
    private final Session session;

    @Autowired
    public ResetService(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Session session) {
        this.session = session;
    }

    @Transactional
    public void clearDatabase() {
        session.purgeDatabase();
    }

    @Transactional
    public void load() {
        try {
            var query = Files
                    .lines(Paths.get(".", "doc", "db_init.cypher"))
                    .collect(Collectors.joining("\n"));
            session.query(query, Collections.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
