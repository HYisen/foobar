package net.alexhyisen.foobar;

import com.google.gson.Gson;
import net.alexhyisen.foobar.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "foobar.enableSecurity=false")
@AutoConfigureMockMvc
public class ProcedureTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private Gson gson;

    @Test
    public void getFriend() throws Exception {
        var result = "[\n" +
                "  {\n" +
                "    \"uid\": 10002,\n" +
                "    \"nickname\": \"Trump\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"uid\": 10003,\n" +
                "    \"nickname\": \"Elder the Frog\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"uid\": 10004,\n" +
                "    \"nickname\": \"Japs\"\n" +
                "  }\n" +
                "]";
        mockMvc.perform(get("/api/10001/friend"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(result));
    }

    @Test
    public void getMoment() throws Exception {
        var result = "[\n" +
                "  {\n" +
                "    \"timestamp\": 1544031602374,\n" +
                "    \"person\": {\n" +
                "      \"uid\": 10001,\n" +
                "      \"nickname\": \"Winnie the Pooh\"\n" +
                "    },\n" +
                "    \"paper\": {\n" +
                "      \"pid\": 400002,\n" +
                "      \"title\": \"Propaganda\",\n" +
                "      \"content\": \"Just shut up and listen to me.\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"timestamp\": 1544031502374,\n" +
                "    \"person\": {\n" +
                "      \"uid\": 10001,\n" +
                "      \"nickname\": \"Winnie the Pooh\"\n" +
                "    },\n" +
                "    \"paper\": {\n" +
                "      \"pid\": 400003,\n" +
                "      \"title\": \"DREAM\",\n" +
                "      \"content\": \"May you rest in a deep and dreamless slumber.\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"timestamp\": 1544031402374,\n" +
                "    \"person\": {\n" +
                "      \"uid\": 10001,\n" +
                "      \"nickname\": \"Winnie the Pooh\"\n" +
                "    },\n" +
                "    \"paper\": {\n" +
                "      \"pid\": 400004,\n" +
                "      \"title\": \"lonely fighter\",\n" +
                "      \"content\": \"Even if no one understand me, I will still struggle to plug the country out.\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"timestamp\": 1544031202374,\n" +
                "    \"person\": {\n" +
                "      \"uid\": 10002,\n" +
                "      \"nickname\": \"Trump\"\n" +
                "    },\n" +
                "    \"paper\": {\n" +
                "      \"pid\": 400005,\n" +
                "      \"title\": \"FAKE NEWS!\",\n" +
                "      \"content\": \"MAGA!\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"timestamp\": 1544031002374,\n" +
                "    \"person\": {\n" +
                "      \"uid\": 10003,\n" +
                "      \"nickname\": \"Elder the Frog\"\n" +
                "    },\n" +
                "    \"paper\": {\n" +
                "      \"pid\": 400006,\n" +
                "      \"title\": \"claim\",\n" +
                "      \"content\": \"I'm not died until you die.\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"timestamp\": 1544030802374,\n" +
                "    \"person\": {\n" +
                "      \"uid\": 10004,\n" +
                "      \"nickname\": \"Japs\"\n" +
                "    },\n" +
                "    \"paper\": {\n" +
                "      \"pid\": 400007,\n" +
                "      \"title\": \"title\",\n" +
                "      \"content\": \"content\"\n" +
                "    }\n" +
                "  }\n" +
                "]";
        mockMvc.perform(get("/api/10001/moment"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(result));
    }

    @Test
    public void getStranger() throws Exception {
        var result = "[\n" +
                "  {\n" +
                "    \"uid\": 10002,\n" +
                "    \"nickname\": \"Trump\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"uid\": 10004,\n" +
                "    \"nickname\": \"Japs\"\n" +
                "  }\n" +
                "]";
        mockMvc.perform(get("/api/10003/stranger"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(result));
    }

    @Test
    public void getAgent() throws Exception {
        var result = "[\n" +
                "  {\n" +
                "    \"uid\": 10001,\n" +
                "    \"nickname\": \"Winnie the Pooh\"\n" +
                "  }\n" +
                "]";
        mockMvc.perform(get("/api/10003/agent/10004"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(result));
    }

    @Test
    @Transactional
    public void postPaper() throws Exception {
        final Long uid = 10000L;
        final String title = "Brand New Paper";
        final String content = "A new HTTP Client?";

        var paper = new Paper();
        paper.setTitle(title);
        paper.setContent(content);
        var json = gson.toJson(paper);

        var result = mockMvc
                .perform(post("/api/" + uid + "/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        var publication = gson.fromJson(result.getResponse().getContentAsString(), Publication.class);
        System.out.println(publication);

        assertNotNull(publication);
        assertEquals(uid, publication.getPerson().getUid());
        assertEquals(title, publication.getPaper().getTitle());
        assertEquals(content, publication.getPaper().getContent());
        var pid = publication.getPaper().getPid();


        //Even identical papers can be published, but their uid varies.
        result = mockMvc
                .perform(post("/api/" + uid + "/paper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        publication = gson.fromJson(result.getResponse().getContentAsString(), Publication.class);
        System.out.println(publication);

        assertNotNull(publication);
        assertEquals(uid, publication.getPerson().getUid());
        assertEquals(title, publication.getPaper().getTitle());
        assertEquals(content, publication.getPaper().getContent());
        assertNotEquals(pid, publication.getPaper().getPid());
    }

    @Test
    @Transactional
    public void deletePaper() throws Exception {
        final long pid = 4000002L;
        final long uid = 10001L;

        mockMvc
                .perform(delete("/api/" + uid + "/paper/" + pid))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void postUser() throws Exception {
        final String username = "omega01";
        final String password = "z";
        final String nickname = "Finalize";

        var info = new RegisterInfo();
        info.setUsername(username);
        info.setPassword(password);
        info.setNickname(nickname);

        //First time everything should go well.
        var result = mockMvc
                .perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(info)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        var link = gson.fromJson(result.getResponse().getContentAsString(), Link.class);
        System.out.println(link);

        assertNotNull(link);
        assertEquals(username, link.getAccount().getUsername());
        assertTrue(passwordEncoder.matches(password, link.getAccount().getPassword()));
        assertEquals(nickname, link.getPerson().getNickname());

        //Second time register with identical username shall return 409.
        info.setPassword(password + "_another");
        info.setNickname(nickname + "_another");
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(info)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @Transactional
    public void friendship() throws Exception {
        final Long aUid = 10002L;
        final Long bUid = 10000L;

        final String msg = "Long time no see, old friend.";

        //A create invitation to B
        var result = mockMvc
                .perform(post("/api/" + aUid + "/invite/" + bUid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(msg))
                .andExpect(status().isOk())
                .andReturn();

        var invitation = gson.fromJson(result.getResponse().getContentAsString(), Invitation.class);
        assertEquals(aUid, invitation.getSrc().getUid());
        assertEquals(bUid, invitation.getDst().getUid());


        //A can find its export invitations
        result = mockMvc
                .perform(get("/api/" + aUid + "/invite/export"))
                .andExpect(status().isOk())
                .andReturn();

        var invitations = gson.fromJson(result.getResponse().getContentAsString(), Invitation[].class);
        invitation = invitations[0];
        assertEquals(aUid, invitation.getSrc().getUid());
        assertEquals(bUid, invitation.getDst().getUid());


        //B can find its import invitations
        result = mockMvc
                .perform(get("/api/" + bUid + "/invite/import"))
                .andExpect(status().isOk())
                .andReturn();

        invitations = gson.fromJson(result.getResponse().getContentAsString(), Invitation[].class);
        invitation = invitations[0];
        assertEquals(aUid, invitation.getSrc().getUid());
        assertEquals(bUid, invitation.getDst().getUid());


        //A can delete its export invitations
        mockMvc
                .perform(delete("/api/" + aUid + "/invite/export/" + bUid))
                .andExpect(status().isOk());


        //B can delete its import invitations
        mockMvc
                .perform(delete("/api/" + bUid + "/invite/import/" + aUid))
                .andExpect(status().isOk());


        //Both A and B can delete multiple times, everything is fine.


        //recover the deleted invitation first
        mockMvc
                .perform(post("/api/" + aUid + "/invite/" + bUid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(msg));
        //B can accept the invitation
        mockMvc
                .perform(post("/api/" + bUid + "/accept/" + aUid))
                .andExpect(status().isOk())
                .andReturn();


        //Either A or B can break the friendship
        mockMvc
                .perform(delete("/api/" + bUid + "/friend/" + aUid))
                .andExpect(status().isOk())
                .andReturn();
    }
}
