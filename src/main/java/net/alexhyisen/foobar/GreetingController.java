package net.alexhyisen.foobar;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @RequestMapping("/greeting")
    public Greeting greeting() {
        return new Greeting(17, "ECHO");
    }
}
