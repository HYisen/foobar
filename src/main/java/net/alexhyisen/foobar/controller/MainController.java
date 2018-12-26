package net.alexhyisen.foobar.controller;

import net.alexhyisen.foobar.module.*;
import net.alexhyisen.foobar.security.UserService;
import net.alexhyisen.foobar.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
public class MainController {
    private final MainService mainService;
    private final UserService userService;
    @Value("${foobar.anonymousUid}")
    private String anonymousUid;

    @Autowired
    public MainController(MainService mainService, UserService userService) {
        this.mainService = mainService;
        this.userService = userService;
    }

    @GetMapping("/api/{uid}/paper")
    public Collection<Publication> papers(Principal principal,
                                          @PathVariable long uid,
                                          @RequestParam(value = "skip", defaultValue = "0") long skip,
                                          @RequestParam(value = "limit", defaultValue = "10") long limit) {
        var name = principal == null ? anonymousUid : principal.getName();
        return mainService.findPublications(Long.valueOf(name), uid, skip, limit);
    }

    @GetMapping("/api/{uid}/friend")
    public Collection<Person> friends(@PathVariable long uid,
                                      @RequestParam(value = "skip", defaultValue = "0") long skip,
                                      @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findFriends(uid, skip, limit);
    }

    @GetMapping("/api/{uid}/moment")
    public Collection<Publication> moments(@PathVariable long uid,
                                           @RequestParam(value = "skip", defaultValue = "0") long skip,
                                           @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findMoments(uid, skip, limit);
    }

    @GetMapping("/api/{uid}/stranger")
    public Collection<Person> strangers(@PathVariable long uid,
                                        @RequestParam(value = "skip", defaultValue = "0") long skip,
                                        @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findStrangers(uid, skip, limit);
    }

    @GetMapping("/api/{srcUid}/agent/{dstUid}")
    public Collection<Person> agents(@PathVariable long srcUid, @PathVariable long dstUid,
                                     @RequestParam(value = "skip", defaultValue = "0") long skip,
                                     @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findAgents(srcUid, dstUid, skip, limit);
    }

    @PostMapping("/api/{uid}/paper")
    public Publication createPaper(@PathVariable long uid, @RequestBody Paper paper) {
        return mainService.addPaper(uid, paper.getTitle(), paper.getContent());
    }

    @DeleteMapping("/api/{uid}/paper/{pid}")
    public void removePaper(@PathVariable long uid, @PathVariable long pid) {
        mainService.delPaper(uid, pid);
    }

    @PostMapping("/api/register")
    public Link createUser(@RequestBody RegisterInfo info) {
        return mainService.addUser(info);
    }

    @PostMapping("/api/{srcUid}/invite/{dstUid}")
    public Invitation addInvitation(@PathVariable long srcUid, @PathVariable long dstUid, @RequestBody String message) {
        return mainService.createInvitation(srcUid, dstUid, message);
    }

    @DeleteMapping("/api/{srcUid}/invite/{dstUid}")
    @Deprecated//use delExportInvitation() instead
    public void delInvitation(@PathVariable long srcUid, @PathVariable long dstUid) {
        delExportInvitation(srcUid, dstUid);
    }

    @DeleteMapping("/api/{dstUid}/invite/import/{srcUid}")
    public void delImportInvitation(@PathVariable long srcUid, @PathVariable long dstUid) {
        mainService.deleteInvitation(srcUid, dstUid);
    }

    @DeleteMapping("/api/{srcUid}/invite/export/{dstUid}")
    public void delExportInvitation(@PathVariable long srcUid, @PathVariable long dstUid) {
        mainService.deleteInvitation(srcUid, dstUid);
    }

    @GetMapping("/api/{uid}/invite/import")
    public Collection<Invitation> importInvitations(@PathVariable long uid,
                                                    @RequestParam(value = "skip", defaultValue = "0") long skip,
                                                    @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findImportInvitations(uid, skip, limit);
    }

    @GetMapping("/api/{uid}/invite/export")
    public Collection<Invitation> exportInvitations(@PathVariable long uid,
                                                    @RequestParam(value = "skip", defaultValue = "0") long skip,
                                                    @RequestParam(value = "limit", defaultValue = "10") long limit) {
        return mainService.findExportInvitations(uid, skip, limit);
    }

    @PostMapping("/api/{invitationDstUid}/accept/{invitationSrcUid}")
    public void acceptInvitation(@PathVariable long invitationSrcUid, @PathVariable long invitationDstUid) {
        mainService.acceptInvitation(invitationSrcUid, invitationDstUid);
    }

    @DeleteMapping("/api/{srcUid}/friend/{dstUid}")
    public void breakFriendship(@PathVariable long srcUid, @PathVariable long dstUid) {
        mainService.breakFriendship(srcUid, dstUid);
    }

    @PostMapping("/api/{uid}/password")
    public Link updatePassword(@PathVariable long uid, @RequestBody String password) {
        return userService.updatePassword(uid, password);
    }

    @PostMapping("/api/{uid}/userinfo")
    public boolean mysubmit(@PathVariable long uid, @RequestBody UserInfo userInfo) {
        String nickname = userInfo.getNickname();
        String password = userInfo.getNewPassword();
        Long u0=null, u1=null;
        if (!nickname.isBlank()) {
            u0 = mainService.updateNickname(uid, userInfo.getOldPassword(), nickname);
        }
        if (!password.isEmpty()) {
            u1 = mainService.updatePassword(uid, userInfo.getOldPassword(), password);
        }
        return u0 != null || u1 != null;
    }

}
