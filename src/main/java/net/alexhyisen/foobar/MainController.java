package net.alexhyisen.foobar;

import net.alexhyisen.foobar.module.Person;
import net.alexhyisen.foobar.module.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class MainController {
    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/{uid}/paper")
    public Collection<Publication> papers(@PathVariable long uid,
                                          @RequestParam(value = "skip", defaultValue = "0") long skip,
                                          @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findPublications(uid, skip, limit);
    }

    @GetMapping("/{uid}/friend")
    public Collection<Person> friends(@PathVariable long uid,
                                      @RequestParam(value = "skip", defaultValue = "0") long skip,
                                      @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findFriends(uid, skip, limit);
    }

    @GetMapping("/{uid}/moment")
    public Collection<Publication> moments(@PathVariable long uid,
                                           @RequestParam(value = "skip", defaultValue = "0") long skip,
                                           @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findMoments(uid, skip, limit);
    }
}
