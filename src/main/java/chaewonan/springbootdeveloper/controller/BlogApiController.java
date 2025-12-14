package chaewonan.springbootdeveloper.controller;

import chaewonan.springbootdeveloper.domain.Article;
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
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            Principal principal
    ) {
        String author = principal.getName();

        Article savedArticle = blogService.save(title, content, author);

        if (images != null && !images.isEmpty()) {
            imageService.saveImages(images, savedArticle);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) {
        Article updatedArticle = blogService.update(id, title, content);

        if (images != null && !images.isEmpty()) {
            imageService.saveImages(images, updatedArticle);
        }

        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        blogService.delete(id);
    }
}
