package chaewonan.springbootdeveloper.controller;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.dto.ArticleListViewResponse;
import chaewonan.springbootdeveloper.dto.ArticleViewResponse;
import chaewonan.springbootdeveloper.service.BlogService;
import chaewonan.springbootdeveloper.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;
    private final LikeService likeService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {

        // 1. 게시글 조회
        Article article = blogService.findById(id);

        // 2. 기본 liked 값 false
        boolean liked = false;

        // 3. 인증 객체 가져오기
        var auth = SecurityContextHolder.getContext().getAuthentication();

        // 4. 로그인 상태일 경우에만 좋아요 여부 조회
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            liked = likeService.isLikedByMe(id);
        }

        // 5. debug log
        System.out.println("liked 상태 = " + liked);

        // 6. 모델에 값 담기
        model.addAttribute("article", new ArticleViewResponse(article));
        model.addAttribute("liked", liked);

        return "article";
    }



    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
