package net.alexhyisen.foobar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class WebController {
    @Value("${foobar.anonymousUid}")
    private String anonymousUid;
    @Value("${foobar.anonymousNickname}")
    private String anonymousNickname;

    private MainService mainService;

    @Autowired
    public WebController(MainService mainService) {
        this.mainService = mainService;
    }


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
        injectPerson(model, principal);
        return "moments";
    }

    @GetMapping(path = "/register")
    public String register() {
        return "register";
    }

    private void injectPerson(Model model, Principal principal) {
        var uid = anonymousUid;
        var nickname = anonymousNickname;
        if (principal != null) {
            uid = principal.getName();
            final var person = mainService.findPersonByUid(Long.valueOf(uid));
            nickname = person.getNickname();
            assert person.getUid().toString().equals(uid);
        }
        model.addAttribute("uid", uid);
        model.addAttribute("nickname", nickname);
    }
}
