package com.example.articleservice.repository;

import com.example.articleservice.model.Article;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT COUNT(a) FROM Article a WHERE a.publishingDate >= :sevenDays")
    Integer countArticlesWrittenLastWeek(@Param("sevenDays") LocalDateTime sevenDays);

    Page<Article> findAll(Pageable pageable);
}
