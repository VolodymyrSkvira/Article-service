package com.example.articleservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.articleservice.dto.article.ArticleRequestDto;
import com.example.articleservice.dto.article.ArticleResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerTests {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Sql(scripts = "classpath:database/articles/delete-all-from-articles.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(authorities = "USER")
    @Test
    @DisplayName("Create a valid article")
    public void create_validArticle_ShouldReturnArticleResponseDto() throws Exception {
        ArticleRequestDto requestDto = new ArticleRequestDto(
                "Test title",
                "Test author",
                "Test content",
                LocalDateTime.now()
        );

        ArticleResponseDto expected = new ArticleResponseDto(
                "Test title",
                "Test author",
                "Test content",
                LocalDateTime.now()
        );

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/articles")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ArticleResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                ArticleResponseDto.class);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("publishingDate")
                .isEqualTo(expected);
    }

    @Sql(scripts = "classpath:database/articles/add-articles-to-database.sql")
    @Sql(scripts = "classpath:database/articles/delete-all-from-articles.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(authorities = "USER")
    @Test
    @DisplayName("Receive all articles")
    public void getAllArticles_validData_shouldReturnListOfArticleResponseDto() throws Exception {
        ArticleResponseDto[] expected = {new ArticleResponseDto(
                "First title",
                "First author",
                "First content",
                LocalDateTime.now()),
                new ArticleResponseDto(
                        "Second title",
                        "Second author",
                        "Second content",
                        LocalDateTime.now()),
                new ArticleResponseDto(
                        "Third title",
                        "Third author",
                        "Third content",
                        LocalDateTime.now())};

        MvcResult result = mockMvc.perform(
                        get("/articles")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ArticleResponseDto[] actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                ArticleResponseDto[].class);

        assertEquals(3, actual.length);
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("publishingDate")
                .containsExactlyInAnyOrder(expected);
    }

    @Sql(scripts = "classpath:database/articles/add-articles-to-database.sql")
    @Sql(scripts = "classpath:database/articles/delete-all-from-articles.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(authorities = "ADMIN")
    @Test
    @DisplayName("Receive article count for the last week")
    public void getAllArticlesWithin7Days_validArticles_shouldReceiveInteger() throws Exception {
        Integer expected = 3;

        MvcResult result = mockMvc.perform(
                        get("/articles/week")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Integer actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                Integer.class);

        assertEquals(actual, expected);
    }

    @Sql(scripts = "classpath:database/articles/add-articles-to-database.sql")
    @Sql(scripts = "classpath:database/articles/delete-all-from-articles.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(authorities = "ADMIN")
    @Test
    @DisplayName("Delete article by id")
    public void deleteArticleById_validData_shouldDoNothing() throws Exception {
        ResultActions result = mockMvc.perform(
                        delete("/articles/3")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }
}
