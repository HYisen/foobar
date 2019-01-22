package net.alexhyisen.foobar.controller;

import net.alexhyisen.foobar.security.AdminProperties;
import net.alexhyisen.foobar.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.security.Principal;

@Controller
public class WebController {
    @Value("${foobar.anonymousUid}")
    private String anonymousUid;
    @Value("${foobar.anonymousNickname}")
    private String anonymousNickname;

    private final MainService mainService;

    private final AdminProperties adminProperties;

    @Autowired
    public WebController(MainService mainService, AdminProperties adminProperties) {
        this.mainService = mainService;
        this.adminProperties = adminProperties;
    }


    @GetMapping(path = "/heaven")
    public String heaven() {
        return "heaven";
    }

    @GetMapping(path = "/index")
    public String index() {
        return "my_index";
    }

    @GetMapping(path = "/")
    public String slash() {
        return "login";
    }

    @GetMapping(path = "/moments")
    public String moments(Model model, Principal principal) {
        injectPerson(model, principal);
        return "moments";
    }

    @GetMapping(path = "/user")
    public String user(Model model, Principal principal) {
        injectPerson(model, principal);
        return "user";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "login" ;
    }

    @GetMapping(path = "/register")
    public String register() {
        return "register";
    }

    @GetMapping(path = "/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    private void injectPerson(Model model, Principal principal) {
        var uid = anonymousUid;
        var nickname = anonymousNickname;
        if (principal != null) {
            uid = principal.getName();
            if (adminProperties.getUid().equals(uid)) {
                nickname = adminProperties.getNickname();
            } else {
                final var person = mainService.findPersonByUid(Long.valueOf(uid));
                nickname = person.getNickname();
                assert person.getUid().toString().equals(uid);
            }
        }
        model.addAttribute("uid", uid);
        model.addAttribute("nickname", nickname);
    }
}
