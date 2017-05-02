package de.micromata.spring.security.example.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author Jürgen Fast - j.fast@micromata.de
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void getIndexWithoutLogin() throws Exception{
        ResultActions action = mvc.perform(get("/"));

        int status = action.andReturn().getResponse().getStatus();
        assertTrue("expected status code = 302 ; current status code = " + status, status == 302);

        String redirectURL = action.andReturn().getResponse().getHeader("Location");
        assertTrue("no redirect to the login page", "http://localhost/login".equals(redirectURL));
    }

    @Test
    public void getIndexWithLogin() throws Exception{
        ResultActions action = mvc.perform(get("/").with(user("user")));
        
        int status = action.andReturn().getResponse().getStatus();
        assertTrue("expected status code = 200 ; current status code = " + status, status == 200);
    }

    @Test
    public void getConsoleWithLoginUser() throws Exception{
        ResultActions action = mvc.perform(get("/console").with(user("user")));

        int status = action.andReturn().getResponse().getStatus();
        assertTrue("expected status code = 403 ; current status code = " + status, status == 403);
    }

    @Test
    public void getConsoleWithLoginAdmin() throws Exception{
        ResultActions action = mvc.perform(post("/console").with(user("admin").roles("ADMIN")));

        int status = action.andReturn().getResponse().getStatus();
        assertTrue("expected status code = 404 ; current status code = " + status, status == 404);
    }

    @Test
    public void getNoSecurity() throws Exception{
        ResultActions action = mvc.perform(get("/noSecurity"));

        int status = action.andReturn().getResponse().getStatus();
        assertTrue("expected status code = 200 ; current status code = " + status, status == 200);
    }

    @Test
    public void checkCsrfWithToken() throws Exception{
        ResultActions action = mvc.perform(post("/noSecurity").with(csrf()).with(user("user")));

        int status = action.andReturn().getResponse().getStatus();
        assertTrue("expected status code = 200 ; current status code = " + status, status == 200);
    }

    @Test
    public void checkCsrfWithOutToken() throws Exception{
        ResultActions action = mvc.perform(post("/noSecurity").with(user("user")));
        int status = action.andReturn().getResponse().getStatus();
        assertTrue("expected status code = 403 ; current status code = " + status, status == 403);
    }

    @Test
    public void checkLogin() throws Exception{
        ResultActions action = mvc.perform(formLogin("/login").user("admin").password("password"));

        int status = action.andReturn().getResponse().getStatus();
        assertTrue("expected status code = 302 ; current status code = " + status, status == 302);

        String redirectURL = action.andReturn().getResponse().getHeader("Location");
        assertTrue("login with valid user and password not possible", "/".equals(redirectURL));
    }

    @Test
    public void checkLoginWithWrongPassword() throws Exception{
        ResultActions action = mvc.perform(formLogin("/login").user("admin").password("pass"));


        int status = action.andReturn().getResponse().getStatus();
        assertTrue("expected status code = 302 ; current status code = " + status, status == 302);

        String redirectURL = action.andReturn().getResponse().getHeader("Location");
        assertTrue("possible login with wrong password", "/login?error".equals(redirectURL));
    }
}
