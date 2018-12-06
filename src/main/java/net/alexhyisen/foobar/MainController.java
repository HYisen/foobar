package net.alexhyisen.foobar;

import net.alexhyisen.foobar.module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{uid}/stranger")
    public Collection<Person> strangers(@PathVariable long uid,
                                        @RequestParam(value = "skip", defaultValue = "0") long skip,
                                        @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findStrangers(uid, skip, limit);
    }

    @GetMapping("/{srcUid}/agent/{dstUid}")
    public Collection<Person> agents(@PathVariable long srcUid, @PathVariable long dstUid,
                                     @RequestParam(value = "skip", defaultValue = "0") long skip,
                                     @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findAgents(srcUid, dstUid, skip, limit);
    }

    @PostMapping("/{uid}/paper")
    public Publication createPaper(@PathVariable long uid, @RequestBody Paper paper) {
        return mainService.addPaper(uid, paper.getTitle(), paper.getContent());
    }

    @DeleteMapping("/{uid}/paper/{pid}")
    public void removePaper(@PathVariable long uid, @PathVariable long pid) {
        mainService.delPaper(uid, pid);
    }

    @PostMapping("/register")
    public Link createUser(@RequestBody RegisterInfo info) {
        return mainService.addUser(info);
    }
}
