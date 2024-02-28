package com.example.articleservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.articleservice.dto.article.ArticleRequestDto;
import com.example.articleservice.dto.article.ArticleResponseDto;
import com.example.articleservice.service.ArticleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Article management", description = "Endpoints for managing articles")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    @Operation(summary = "Create article",
            description = "Create a new article")
    public ArticleResponseDto createArticle(@RequestBody @Valid ArticleRequestDto articleRequestDto) {
        return articleService.create(articleRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get all articles",
            description = "Get a list of all articles")
    public List<ArticleResponseDto> getAllArticles(@PageableDefault(size = 5) Pageable pageable) {
        return articleService.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/week")
    @Operation(summary = "Get all articles within 7 days",
            description = "Get all articles within the last 7 days")
    public Integer getAllArticlesWithin7Days() {
        return articleService.countArticlesWrittenLastWeek();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete article by id",
            description = "Delete article by id")
    public void deleteArticleById(@PathVariable @Positive Long id) {
        articleService.deleteById(id);
    }
}
