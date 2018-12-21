package net.alexhyisen.foobar.service;

import net.alexhyisen.foobar.module.*;
import net.alexhyisen.foobar.repository.AccountRepository;
import net.alexhyisen.foobar.repository.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Service
public class MainService {
    private final MainRepository mainRepository;
    private final AccountRepository accountRepository;

    private static long prevPid = 0;
    private static long prevUid = 0;

    @Autowired
    public MainService(MainRepository mainRepository, AccountRepository accountRepository) {
        this.mainRepository = mainRepository;
        this.accountRepository = accountRepository;
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
            prevUid = mainRepository.findMaxUid();
        }
        return ++prevUid;
    }

    public Link addUser(RegisterInfo info) {
        final var username = info.getUsername();
        if (accountRepository.existsByUsername(username)) {
            throw new UsernameExistsException();
        } else {
            return mainRepository.addUser(username, info.getPassword(), getNextUid(), info.getNickname());
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

    public Long updatePassword(long uid,String oldPassword,String newPassword) {
        return mainRepository.updatePassword(uid,oldPassword, newPassword);
    }

    public Long updateNickname(long uid,String oldPassword,String nickname) {
        return mainRepository.updateNickname(uid,oldPassword, nickname);
    }



}
