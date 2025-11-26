package chaewonan.springbootdeveloper.service;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.domain.Like;
import chaewonan.springbootdeveloper.domain.User;
import chaewonan.springbootdeveloper.repository.BlogRepository;
import chaewonan.springbootdeveloper.repository.LikeRepository;
import chaewonan.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public void like(Long postId, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Article article = blogRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        // 이미 좋아요 눌렀으면 무시
        if (likeRepository.findByUserAndPost(user, article).isPresent()) {
            return;
        }

        Like like = Like.builder()
                .user(user)
                .post(article)
                .build();

        likeRepository.save(like);
    }

    @Transactional
    public void unlike(Long postId, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Article article = blogRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        Like like = likeRepository.findByUserAndPost(user, article)
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));

        likeRepository.delete(like);
    }
}
