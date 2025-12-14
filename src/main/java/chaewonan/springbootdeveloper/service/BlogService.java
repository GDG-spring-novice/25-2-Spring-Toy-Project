package chaewonan.springbootdeveloper.service;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(String title, String content, String author) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .author(author)
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

    public Article update(Long id, String title, String content) {
        Article article = findById(id);
        article.update(title, content);
        return blogRepository.save(article);
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
