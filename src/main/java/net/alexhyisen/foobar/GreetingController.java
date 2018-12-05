package net.alexhyisen.foobar;

import net.alexhyisen.foobar.module.Greeting;
import net.alexhyisen.foobar.module.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {
    private final MainService mainService;

    @Autowired
    public GreetingController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping("/greeting")
    public Greeting greeting() {
        return new Greeting(17, "ECHO");
    }

    @GetMapping("/fuck")
    public List<Paper> fuck() {
        return mainService.findFriendsPapers(10001L);
    }
}
