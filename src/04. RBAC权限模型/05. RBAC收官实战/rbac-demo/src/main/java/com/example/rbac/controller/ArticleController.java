package com.example.rbac.controller;

import com.example.rbac.dto.ApiResponse;
import com.example.rbac.dto.ArticleRequest;
import com.example.rbac.entity.Article;
import com.example.rbac.service.ArticleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('article:read')")
    public ApiResponse<List<Article>> getAll() {
        return ApiResponse.success(articleService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('article:read')")
    public ApiResponse<Article> getOne(@PathVariable Long id) {
        return ApiResponse.success(articleService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('article:create')")
    public ApiResponse<Article> create(@RequestBody ArticleRequest request) {
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setAuthorId(request.getAuthorId());
        return ApiResponse.success("创建成功", articleService.create(article));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('article:update')")
    public ApiResponse<Article> update(@PathVariable Long id, @RequestBody ArticleRequest request) {
        Article existing = articleService.findById(id);
        if (existing == null) {
            return ApiResponse.error(404, "文章不存在");
        }
        existing.setTitle(request.getTitle());
        existing.setContent(request.getContent());
        existing.setAuthorId(request.getAuthorId());
        return ApiResponse.success("更新成功", articleService.update(existing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('article:delete')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        articleService.deleteById(id);
        return ApiResponse.success("删除成功", null);
    }
}
