package net.alexhyisen.foobar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping(path = "/heaven")
    public String heaven() {
        return "heaven";
    }

    @GetMapping(path = "/index")
    public String index() {
        return "index";
    }

    @GetMapping(path = "/moments")
    public String moments() {
        return "moments";
    }

    @GetMapping(path = "/register")
    public String register() {
        return "register";
    }
}
