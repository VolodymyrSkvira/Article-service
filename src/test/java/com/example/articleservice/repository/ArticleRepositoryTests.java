package com.example.articleservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryTests {
    @Autowired
    private ArticleRepository articleRepository;

    @Sql(scripts = "classpath:database/articles/add-articles-to-database.sql")
    @Test
    public void countArticlesWrittenLastWeek_validData_shouldReturnAnInteger() {
        Integer expected = 3;
        LocalDateTime sevenDays = LocalDateTime.now().minusDays(7);

        Integer actual = articleRepository.countArticlesWrittenLastWeek(sevenDays);

        assertEquals(actual, expected);
    }
}
