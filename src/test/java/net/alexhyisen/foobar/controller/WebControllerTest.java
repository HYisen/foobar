package net.alexhyisen.foobar.controller;

import net.alexhyisen.foobar.security.AdminProperties;
import net.alexhyisen.foobar.security.LinkRepository;
import net.alexhyisen.foobar.security.WebSecurityConfig;
import net.alexhyisen.foobar.service.MainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@WebMvcTest(value = WebController.class, properties = "foobar.enableSecurity=false")
public class WebControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MainService mainService;
    @MockBean
    private AdminProperties adminProperties;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @MockBean
    private LinkRepository linkRepository;

    @Test
    public void slashShouldLogin() throws Exception {
        mockMvc
                .perform(get("/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
