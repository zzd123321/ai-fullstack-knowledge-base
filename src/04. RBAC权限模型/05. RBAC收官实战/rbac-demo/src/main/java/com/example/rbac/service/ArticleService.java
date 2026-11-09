package com.example.rbac.service;

import com.example.rbac.entity.Article;
import com.example.rbac.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Transactional
    public Article create(Article article) {
        article.setCreatedAt(OffsetDateTime.now());
        article.setUpdatedAt(OffsetDateTime.now());
        return articleRepository.save(article);
    }

    @Transactional
    public Article update(Article article) {
        article.setUpdatedAt(OffsetDateTime.now());
        return articleRepository.save(article);
    }

    @Transactional
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }
}
