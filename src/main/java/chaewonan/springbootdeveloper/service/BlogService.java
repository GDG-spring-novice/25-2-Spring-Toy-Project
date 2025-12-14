package chaewonan.springbootdeveloper.service;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.dto.AddArticleRequest;
import chaewonan.springbootdeveloper.dto.UpdateArticleRequest;
import chaewonan.springbootdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, String author, List<String> imageUrls) {
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

    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        article.update(request.getTitle(), request.getContent());
        return blogRepository.save(article);
    }

    public Article updateImages(Long id, List<String> imageUrls) {
        Article article = findById(id);
        article.setImageUrls(imageUrls);
        return blogRepository.save(article);
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
