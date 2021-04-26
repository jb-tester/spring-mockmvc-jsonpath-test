package com.mytests.spring.mockmvcJsonPathTest;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class JsonPathAssertionTests {

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(new TeamController())
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON))
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType("application/json"))
                .build();
    }


    @Test
    public void testPresence() throws Exception {
        String dev = "$.developers[?(@.name == '%s')]";

        this.mockMvc.perform(get("/team/people"))
                .andExpect(jsonPath(dev, "vasya").exists())
                .andExpect(jsonPath(dev, "pasha").exists())
                .andExpect(jsonPath("$.testers[?(@.name == '%s')]", "vova").exists())
                .andExpect(jsonPath("$.testers[?(@.name == '%s')]", "dasha").exists())
                .andExpect(jsonPath("$.testers[1]").exists())
                .andExpect(jsonPath("$.manager[0]").exists())
                .andExpect(jsonPath("$.developers[2]").exists())
                .andExpect(jsonPath("$.developers[3]").exists());
    }

    @Test
    public void testAbsence() throws Exception {
        this.mockMvc.perform(get("/team/people"))
                .andExpect(jsonPath("$.developers[?(@.name == 'tolya')]").doesNotExist())
                .andExpect(jsonPath("$.developers[?(@.name == 'zina')]").doesNotExist())
                .andExpect(jsonPath("$.developers[10]").doesNotExist());
    }

    @Test
    public void testEqual() throws Exception {
        this.mockMvc.perform(get("/team/people"))
                .andExpect(jsonPath("$.developers[0].name").value("vasya"))
                .andExpect(jsonPath("$.testers[1].name").value("dasha"));


        this.mockMvc.perform(get("/team/people"))
                .andExpect(jsonPath("$.support[0].name").value(equalTo("vanya")))
                .andExpect(jsonPath("$.manager[0].name").value(equalTo("anya")));
    }

    @Test
    public void testMatch() throws Exception {
        this.mockMvc.perform(get("/team/people"))
                .andExpect(jsonPath("$.developers[0].name", startsWith("va")))
                .andExpect(jsonPath("$.testers[0].name", endsWith("va")))
                .andExpect(jsonPath("$.testers[1].name", containsString("da")))
                .andExpect(jsonPath("$.developers[1].name", is(in(Arrays.asList("valya", "anya")))));
    }

    @Test
    public void testParametrized() throws Exception {
        String dev = "$.developers[%s].name";

        this.mockMvc.perform(get("/team/people"))
                .andExpect(jsonPath(dev, 0).value(startsWith("va")))
                .andExpect(jsonPath("$.testers[%s].name", 0).value(endsWith("va")))
                .andExpect(jsonPath("$.testers[%s].name", 1).value(containsString("d")))
                .andExpect(jsonPath(dev, 1).value(is(in(Arrays.asList("pasha", "valya")))));
    }
}

