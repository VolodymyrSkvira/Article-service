package com.example.articleservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.articleservice.dto.article.ArticleRequestDto;
import com.example.articleservice.dto.article.ArticleResponseDto;
import com.example.articleservice.mapper.ArticleMapper;
import com.example.articleservice.model.Article;
import com.example.articleservice.repository.ArticleRepository;
import com.example.articleservice.service.impl.ArticleServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTests {

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private Pageable pageable;

    @Test
    @DisplayName("Create valid article")
    public void create_validArticle_shouldReturnArticleResponseDto() {
        ArticleRequestDto requestDto = new ArticleRequestDto(
                "Test title",
                "Test author",
                "Test content",
                LocalDateTime.now());

        Article article = new Article();
        article.setTitle("Test title");
        article.setAuthor("Test author");
        article.setContent("Test content");
        article.setPublishingDate(LocalDateTime.now());

        ArticleResponseDto expected = new ArticleResponseDto(
                article.getTitle(),
                article.getAuthor(),
                article.getContent(),
                article.getPublishingDate()
        );

        when(articleMapper.toArticle(requestDto)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(article);
        when(articleMapper.toArticleDto(article)).thenReturn(expected);

        ArticleResponseDto actual = articleService.create(requestDto);
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Find all articles")
    public void findAll_validArticle_shouldReturnListOfArticleResponseDto() {
        Article firstArticle = new Article();
        firstArticle.setTitle("First title");
        firstArticle.setAuthor("First author");
        firstArticle.setContent("First content");
        firstArticle.setPublishingDate(LocalDateTime.now());

        Article secondArticle = new Article();
        secondArticle.setTitle("Second title");
        secondArticle.setAuthor("Second author");
        secondArticle.setContent("Second content");
        secondArticle.setPublishingDate(LocalDateTime.now());

        ArticleResponseDto firstDto = new ArticleResponseDto(
                firstArticle.getTitle(),
                firstArticle.getAuthor(),
                firstArticle.getContent(),
                firstArticle.getPublishingDate()
        );

        ArticleResponseDto secondDto = new ArticleResponseDto(
                secondArticle.getTitle(),
                secondArticle.getAuthor(),
                secondArticle.getContent(),
                secondArticle.getPublishingDate()
        );

        List<Article> articles = List.of(firstArticle, secondArticle);
        List<ArticleResponseDto> expected = List.of(firstDto, secondDto);
        Page<Article> articlesPage = new PageImpl<>(articles, pageable, articles.size());

        when(articleRepository.findAll(pageable)).thenReturn(articlesPage);
        when(articleMapper.toArticleDto(firstArticle)).thenReturn(firstDto);
        when(articleMapper.toArticleDto(secondArticle)).thenReturn(secondDto);

        List<ArticleResponseDto> actual = articleService.findAll(pageable);
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Count valid articles written last week")
    public void countArticlesWrittenLastWeek_withValidArticles_shouldReturnAnInteger() {
        Integer expected = 2;
        LocalDateTime sevenDays = LocalDateTime.now().minusDays(7);
        ArticleResponseDto firstDto = new ArticleResponseDto(
                "First title",
                "First author",
                "First content",
                LocalDateTime.now()
        );

        ArticleResponseDto secondDto = new ArticleResponseDto(
                "Second title",
                "Second author",
                "Second content",
                LocalDateTime.now()
        );

        List<ArticleResponseDto> dtoList = List.of(firstDto, secondDto);

        when(articleRepository.countArticlesWrittenLastWeek(Mockito.any(LocalDateTime.class))).thenReturn(expected);
        Integer actual = articleService.countArticlesWrittenLastWeek();

        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Delete article with a valid id")
    public void deleteById_withValidId_shouldDoNothing() {
        Long id = 1L;

        articleService.deleteById(id);

        verify(articleRepository, times(1)).deleteById(id);
    }
}

