package chaewonan.springbootdeveloper.controller;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.service.BlogService;
import chaewonan.springbootdeveloper.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class BlogApiController {

    private final BlogService blogService;
    private final LikeService likeService;

    // 게시글 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Article> addArticle(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            Principal principal
    ) {
        Article article = blogService.save(
                title,
                content,
                principal.getName(),
                images
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    // 게시글 수정 (이미지 포함)
    @PutMapping(value = "/{id}/with-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Article> updateArticleWithImages(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) {
        Article article = blogService.update(id, title, content, images);
        return ResponseEntity.ok(article);
    }

    // 게시글 수정 (텍스트만)
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable long id,
            @RequestBody UpdateArticleRequest request
    ) {
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok(updatedArticle);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
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

    // 내가 좋아요 눌렀는지 확인
    @GetMapping("/{id}/likes/me")
    public ResponseEntity<Boolean> isLikedByMe(@PathVariable Long id) {
        boolean liked = likeService.isLikedByMe(id);
        return ResponseEntity.ok(liked);
    }
}
