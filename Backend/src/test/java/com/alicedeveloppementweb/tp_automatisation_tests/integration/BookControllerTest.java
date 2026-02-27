package com.alicedeveloppementweb.tp_automatisation_tests.integration;

import com.alicedeveloppementweb.tp_automatisation_tests.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_create_book() throws Exception {

        String json = """
            {
              "titre": "Dune",
              "auteur": "Frank Herbert",
              "annee": 1965
            }
        """;

        mockMvc.perform(
                        post("/api/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titre").value("Dune"));
    }

    @Test
    void should_get_all_books() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void should_get_book_by_id() throws Exception {
        // Create a book first to ensure it exists
        String json = """
            {
              "titre": "1984",
              "auteur": "George Orwell",
              "annee": 1949
            }
        """;

        String response = mockMvc.perform(
                post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        long id = ((Number) com.jayway.jsonpath.JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(get("/api/books/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("1984"));
    }

    @Test
    void should_update_book() throws Exception {
        // Create a book first
        String json = """
            {
              "titre": "The Hobbit",
              "auteur": "J.R.R. Tolkien",
              "annee": 1937
            }
        """;

        String response = mockMvc.perform(
                post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        long id = ((Number) com.jayway.jsonpath.JsonPath.read(response, "$.id")).longValue();

        String updatedJson = """
            {
              "titre": "The Hobbit - Updated",
              "auteur": "J.R.R. Tolkien",
              "annee": 1937
            }
        """;

        mockMvc.perform(
                put("/api/books/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titre").value("The Hobbit - Updated"));
    }

    @Test
    void should_delete_book() throws Exception {
        // Create a book first
        String json = """
            {
              "titre": "To Delete",
              "auteur": "Author",
              "annee": 2000
            }
        """;

        String response = mockMvc.perform(
                post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        long id = ((Number) com.jayway.jsonpath.JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(delete("/api/books/" + id))
                .andExpect(status().isOk());
    }
}
