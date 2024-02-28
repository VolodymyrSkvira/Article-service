package com.example.articleservice.service;

import com.example.articleservice.dto.article.ArticleRequestDto;
import com.example.articleservice.dto.article.ArticleResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    ArticleResponseDto create(ArticleRequestDto articleRequestDto);

    List<ArticleResponseDto> findAll(Pageable pageable);

    Integer countArticlesWrittenLastWeek();

    void deleteById(Long id);
}
