package com.example.articleservice.dto.article;

import java.time.LocalDateTime;

public record ArticleResponseDto(
        String title,

        String author,

        String content,

        LocalDateTime publishingDate
) {
}
