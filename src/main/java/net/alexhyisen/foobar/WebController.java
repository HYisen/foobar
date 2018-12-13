package net.alexhyisen.foobar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class WebController {
    @Value("foobar.anonymousUid")
    String anonymousUid;

    @GetMapping(path = "/heaven")
    public String heaven() {
        return "heaven";
    }

    @GetMapping(path = "/index")
    public String index() {
        return "index";
    }

    @GetMapping(path = "/moments")
    public String moments(Model model, Principal principal) {
        var name = anonymousUid;
        if (principal != null) {
            name = principal.getName();
        }
        model.addAttribute("uid", name);
        return "moments";
    }

    @GetMapping(path = "/register")
    public String register() {
        return "register";
    }
}
