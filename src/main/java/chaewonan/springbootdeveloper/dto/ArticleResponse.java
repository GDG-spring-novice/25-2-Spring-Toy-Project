package chaewonan.springbootdeveloper.dto;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.domain.ArticleImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final List<String> imageUrls;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.author = article.getAuthor();
        this.createdAt = article.getCreatedAt();
        this.imageUrls = article.getImages()
                .stream()
                .map(ArticleImage::getImageUrl)
                .collect(Collectors.toList());
    }
}
