package chaewonan.springbootdeveloper.controller;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.service.BlogService;
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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) {
        Article article = blogService.update(id, title, content, images);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        blogService.delete(id);
    }
}
