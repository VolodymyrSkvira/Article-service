package com.example.articleservice.service.impl;

import com.example.articleservice.dto.article.ArticleRequestDto;
import com.example.articleservice.dto.article.ArticleResponseDto;
import com.example.articleservice.mapper.ArticleMapper;
import com.example.articleservice.repository.ArticleRepository;
import com.example.articleservice.service.ArticleService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleResponseDto create(ArticleRequestDto articleRequestDto) {
        return articleMapper.toArticleDto(articleRepository.save(articleMapper.toArticle(articleRequestDto)));
    }

    @Override
    public List<ArticleResponseDto> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable).stream()
                .map(articleMapper::toArticleDto)
                .toList();
    }

    @Override
    public Integer countArticlesWrittenLastWeek() {
        return articleRepository.countArticlesWrittenLastWeek(LocalDateTime.now().minusDays(7));
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }
}
