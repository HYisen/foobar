package net.alexhyisen.foobar.service;

import net.alexhyisen.foobar.model.*;
import net.alexhyisen.foobar.repository.AccountRepository;
import net.alexhyisen.foobar.repository.MainRepository;
import net.alexhyisen.foobar.security.AdminProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class MainServiceTest {
    @SpyBean
    private MainService mainService;

    @MockBean
    private MainRepository mainRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AdminProperties adminProperties;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void findMoments() {
        final long uid = 20000L;
        final long skip = 0L;
        final long limit = 1000L;

        Collection<Publication> dummyData = List.of(new Publication());

        when(mainRepository.findMoments(uid, skip, limit)).thenReturn(dummyData);

        assert mainService.findMoments(uid, skip, limit).equals(dummyData);
    }

    @Test(expected = UsernameExistsException.class)
    public void addUserWithExistsName() {
        final var username = "big_brother";

        var ri = new RegisterInfo();
        ri.setUsername(username);
        ri.setNickname("BB");
        ri.setPassword("qwert");

        //If username exists, return.
        when(accountRepository.existsByUsername(username)).thenReturn(true);
        when(adminProperties.getUsername()).thenReturn("admin");
        mainService.addUser(ri);
    }

    @Test(expected = UsernameExistsException.class)
    public void addUserWithAdminName() {
        final var username = "admin";

        var ri = new RegisterInfo();
        ri.setUsername(username);
        ri.setNickname("BB");
        ri.setPassword("qwert");

        //If username exists as admin username, return.
        when(accountRepository.existsByUsername(username)).thenReturn(false);
        when(adminProperties.getUsername()).thenReturn(username);
        mainService.addUser(ri);
    }

    @Test
    public void addUser() {
        final var username = "big_brother";
        final var adminName = "admin";
        final var rawPassword = "qwert";
        final var encodedPassword = "12345";
        final var nickname = "BB";
        final Long uid = 66666L;

        var ri = new RegisterInfo();
        ri.setUsername(username);
        ri.setNickname(nickname);
        ri.setPassword(rawPassword);

        var account = new Account();
        account.setPassword(encodedPassword);
        account.setUsername(username);
        var person = new Person();
        person.setNickname(nickname);
        person.setUid(uid);
        var link = new Link();
        link.setAccount(account);
        link.setPerson(person);

        //If username exists as admin username, return.
        when(accountRepository.existsByUsername(username)).thenReturn(false);
        when(adminProperties.getUsername()).thenReturn(adminName);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(mainRepository.addUser(eq(username), eq(encodedPassword), any(Long.TYPE), eq(nickname))).thenReturn(link);

        Assert.assertEquals(link, mainService.addUser(ri));
    }
}