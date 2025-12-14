package chaewonan.springbootdeveloper.controller;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.dto.AddArticleRequest;
import chaewonan.springbootdeveloper.dto.UpdateArticleRequest;
import chaewonan.springbootdeveloper.service.BlogService;
import chaewonan.springbootdeveloper.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<Article> addArticle(
            @RequestPart("data") AddArticleRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            Principal principal) {

        String author = principal.getName();

        // 글 먼저 저장
        Article savedArticle = blogService.save(request, author);

        // 이미지 저장
        if (images != null && !images.isEmpty()) {
            imageService.saveImages(images, savedArticle);
        }

        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Article> findAll() {
        return blogService.findAll();
    }

    @GetMapping("/{id}")
    public Article findById(@PathVariable Long id) {
        return blogService.findById(id);
    }

    @PutMapping("/{id}")
    public Article update(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        return blogService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        blogService.delete(id);
    }
}
