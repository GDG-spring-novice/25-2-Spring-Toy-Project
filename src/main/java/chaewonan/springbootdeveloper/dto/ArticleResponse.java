package chaewonan.springbootdeveloper.dto;

import chaewonan.springbootdeveloper.domain.Article;
import lombok.Getter;


@Getter
public class ArticleResponse {

    private final String title;
    private final String content;
    private final int likeCount; // 추가!

    public ArticleResponse(Article article, int likeCount) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.likeCount = likeCount;
    }
}
