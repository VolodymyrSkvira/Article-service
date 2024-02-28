package com.example.articleservice.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record ArticleRequestDto(
        @NotBlank
        @Size(max = 100)
        String title,

        @NotBlank
        String author,

        @NotBlank
        String content,

        @NotNull
        LocalDateTime publishingDate
) {
}
