package chaewonan.springbootdeveloper.service;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogService {

    private final BlogRepository blogRepository;
    private final ImageService imageService;

    public Article save(
            String title,
            String content,
            String author,
            List<MultipartFile> images
    ) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        blogRepository.save(article);

        if (images != null && !images.isEmpty()) {
            imageService.saveImages(images, article);
        }

        return article;
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public Article update(
            Long id,
            String title,
            String content,
            List<MultipartFile> images
    ) {
        Article article = findById(id);
        article.update(title, content);

        article.getImages().clear();

        if (images != null && !images.isEmpty()) {
            imageService.saveImages(images, article);
        }

        return article;
    }


    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
