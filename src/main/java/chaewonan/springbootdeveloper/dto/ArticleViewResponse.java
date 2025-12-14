package chaewonan.springbootdeveloper.dto;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.domain.ArticleImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String author;
    private List<String> imageUrls;

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.author = article.getAuthor();
        this.imageUrls = article.getImages() == null
                ? List.of()
                : article.getImages().stream()
                .map(ArticleImage::getImageUrl)
                .collect(Collectors.toList());
    }
}
