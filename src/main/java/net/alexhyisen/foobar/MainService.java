package net.alexhyisen.foobar;

import net.alexhyisen.foobar.module.MainRepository;
import net.alexhyisen.foobar.module.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MainService {
    private final MainRepository mainRepository;

    @Autowired
    public MainService(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    @Transactional()
    public List<Paper> findFriendsPapers(Long uid) {
        final List<Paper> friendsPapers = mainRepository.findFriendsPapers(uid);
        return friendsPapers;
    }
}
