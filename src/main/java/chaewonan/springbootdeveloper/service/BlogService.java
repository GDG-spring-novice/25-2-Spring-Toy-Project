package chaewonan.springbootdeveloper.service;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.dto.AddArticleRequest;
import chaewonan.springbootdeveloper.dto.UpdateArticleRequest;
import chaewonan.springbootdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, List<MultipartFile> images, String author) {
        List<String> imageUrls = saveImages(images);

        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(author)
                .imageUrls(imageUrls)
                .build();

        return blogRepository.save(article);
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());
        return blogRepository.save(article);
    }

    private List<String> saveImages(List<MultipartFile> images) {
        List<String> urls = new ArrayList<>();
        if (images == null || images.isEmpty()) return urls;

        String uploadDir = "src/main/resources/static/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        for (MultipartFile file : images) {
            if (file.isEmpty()) continue;

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);

            try {
                file.transferTo(dest);
                urls.add("/uploads/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("image upload failed");
            }
        }
        return urls;
    }
}
