package net.alexhyisen.foobar.service;

import net.alexhyisen.foobar.module.*;
import net.alexhyisen.foobar.repository.AccountRepository;
import net.alexhyisen.foobar.repository.MainRepository;
import net.alexhyisen.foobar.security.AdminProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Service
public class MainService {
    private final MainRepository mainRepository;
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final AdminProperties adminProperties;

    private static long prevPid = 0;
    private static long prevUid = 0;

    @Value("${foobar.uidBegin}")
    private Long uidBegin;

    @Autowired
    public MainService(
            MainRepository mainRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            AdminProperties adminProperties
    ) {
        this.mainRepository = mainRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminProperties = adminProperties;
    }

    public Collection<Publication> findPublications(long srcUid, long dstUid, long skip, long limit) {
        return mainRepository.findPublications(srcUid, dstUid, skip, limit);
    }

    public Collection<Person> findFriends(long uid, long skip, long limit) {
        return mainRepository.findFriends(uid, skip, limit);
    }

    public Collection<Publication> findMoments(long uid, long skip, long limit) {
        return mainRepository.findMoments(uid, skip, limit);
    }

    public Collection<Person> findStrangers(long uid, long skip, long limit) {
        return mainRepository.findStrangers(uid, skip, limit);
    }

    public Collection<Person> findAgents(long srcUid, long dstUid, long skip, long limit) {
        return mainRepository.findAgents(srcUid, dstUid, skip, limit);
    }

    public Publication addPaper(long uid, String title, String content) {
        return mainRepository.addPaper(uid, getNextPid(), genTimestamp(), title, content);
    }

    private static long genTimestamp() {
        return Instant.now().toEpochMilli();
    }

    public void delPaper(long uid, long pid) {
        mainRepository.delPaper(uid, pid);
    }

    private long getNextPid() {
        if (prevPid == 0) {
            prevPid = mainRepository.findMaxPid();
        }
        return ++prevPid;
    }

    private long getNextUid() {
        if (prevUid == 0) {
            prevUid = Optional.ofNullable(mainRepository.findMaxUid()).orElse(uidBegin);
        }
        return ++prevUid;
    }

    public Link addUser(RegisterInfo info) {
        final var username = info.getUsername();
        if (accountRepository.existsByUsername(username) || adminProperties.getUsername().equals(username)) {
            throw new UsernameExistsException();
        } else {
            return mainRepository.addUser(
                    username,
                    passwordEncoder.encode(info.getPassword()),
                    getNextUid(),
                    info.getNickname()
            );
        }
    }

    public Invitation createInvitation(long srcUid, long dstUid, String message) {
        return mainRepository.createInvitation(srcUid, dstUid, genTimestamp(), message);
    }

    public void deleteInvitation(long srcUid, long dstUid) {
        mainRepository.deleteInvitation(srcUid, dstUid);
    }

    public Collection<Invitation> findImportInvitations(long uid, long skip, long limit) {
        return mainRepository.findImportInvitations(uid, skip, limit);
    }

    public Collection<Invitation> findExportInvitations(long uid, long skip, long limit) {
        return mainRepository.findExportInvitations(uid, skip, limit);
    }

    public void acceptInvitation(long invitationSrcUid, long invitationDstUid) {
        mainRepository.acceptInvitation(invitationSrcUid, invitationDstUid);
    }

    public void breakFriendship(long srcUid, long dstUid) {
        mainRepository.breakFriendship(srcUid, dstUid);
    }

    public Person findPersonByUid(long uid) {
        return mainRepository.findByUid(uid);
    }

    public Long updatePassword(long uid,String oldRawPassword,String newRawPassword) {
        assert passwordEncoder.matches(oldRawPassword, mainRepository.findPasswordByUid(uid));
        return mainRepository.updatePassword(uid, passwordEncoder.encode(newRawPassword));
    }

    public Long updateNickname(long uid,String oldPassword,String nickname) {
        assert passwordEncoder.matches(oldPassword, mainRepository.findPasswordByUid(uid));
        return mainRepository.updateNickname(uid, nickname);
    }
}
