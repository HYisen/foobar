package net.alexhyisen.foobar;

import net.alexhyisen.foobar.controller.MainController;
import net.alexhyisen.foobar.controller.WebController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {
    @Autowired
    private WebController webController;
    @Autowired
    private MainController mainController;

    @Test
    public void contextLoads() {
        assert webController != null;
        assert mainController != null;
    }
}
