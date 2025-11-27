package chaewonan.springbootdeveloper.controller;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.dto.AddArticleRequest;
import chaewonan.springbootdeveloper.dto.ArticleResponse;
import chaewonan.springbootdeveloper.dto.UpdateArticleRequest;
import chaewonan.springbootdeveloper.service.BlogService;
import chaewonan.springbootdeveloper.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/articles")
public class BlogApiController {

    private final BlogService blogService;
    private final LikeService likeService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request,
                                              java.security.Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articles);
    }

    // 게시글 하나 조회
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok().body(updatedArticle);
    }

    // 좋아요 추가
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable Long id) {
        likeService.like(id);
        return ResponseEntity.ok().build();
    }

    // 좋아요 취소
    @DeleteMapping("/{id}/like")
    public ResponseEntity<Void> unlike(@PathVariable Long id) {
        likeService.unlike(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/likes/me")
    public ResponseEntity<Boolean> isLikedByMe(@PathVariable Long id) {
        boolean liked = likeService.isLikedByMe(id);
        return ResponseEntity.ok(liked);
    }
}
