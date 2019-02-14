package net.alexhyisen.foobar.controller;

import net.alexhyisen.foobar.model.Paper;
import net.alexhyisen.foobar.model.Person;
import net.alexhyisen.foobar.model.Publication;
import net.alexhyisen.foobar.security.AdminProperties;
import net.alexhyisen.foobar.security.LinkRepository;
import net.alexhyisen.foobar.security.UserService;
import net.alexhyisen.foobar.security.WebSecurityConfig;
import net.alexhyisen.foobar.service.MainService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@WebMvcTest(value = MainController.class, properties = "foobar.enableSecurity=false")
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MainService mainService;

    @MockBean
    private UserService userService;

    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @MockBean
    private LinkRepository linkRepository;
    @MockBean
    private AdminProperties adminProperties;

    @Test
    public void momentShouldReturnFromService() throws Exception {
        var paper = new Paper();
        paper.setPid(400001L);
        paper.setTitle("Title");
        paper.setContent("Content");
        var person = new Person();
        person.setUid(10000L);
        person.setNickname("Alpha");
        var publication = new Publication();
        publication.setTimestamp(12345678L);
        publication.setPerson(person);
        publication.setPaper(paper);

        when(mainService.findMoments(10001, 0, 10)).thenReturn(Lists.newArrayList(publication));
        mockMvc.perform(get("/api/10001/moment"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[{\"timestamp\":12345678,\"person\":{\"uid\":10000,\"nickname\":\"Alpha\"},\"paper\":{\"pid\":400001,\"title\":\"Title\",\"content\":\"Content\"}}]"));
    }
}