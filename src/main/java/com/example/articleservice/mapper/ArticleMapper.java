package com.example.articleservice.mapper;

import com.example.articleservice.dto.article.ArticleRequestDto;
import com.example.articleservice.dto.article.ArticleResponseDto;
import com.example.articleservice.model.Article;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl"
)
public interface ArticleMapper {
    ArticleResponseDto toArticleDto(Article article);

    Article toArticle(ArticleRequestDto articleRequestDto);
}
