package chaewonan.springbootdeveloper.service;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.domain.Like;
import chaewonan.springbootdeveloper.domain.User;
import chaewonan.springbootdeveloper.repository.BlogRepository;
import chaewonan.springbootdeveloper.repository.LikeRepository;
import chaewonan.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + username));
    }

    // 좋아요 추가
    public void like(Long postId) {
        User user = getCurrentUser();

        boolean exists = likeRepository.findByUserIdAndPostId(user.getId(), postId).isPresent();
        if (exists) return;

        Article article = blogRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found: " + postId));

        Like like = Like.builder()
                .user(user)
                .post(article)
                .build();

        likeRepository.save(like);
    }

    // 좋아요 취소
    public void unlike(Long postId) {
        User user = getCurrentUser();

        Like like = likeRepository.findByUserIdAndPostId(user.getId(), postId)
                .orElseThrow(() -> new IllegalArgumentException("like not found"));

        likeRepository.delete(like);
    }

    // 좋아요 상태 확인
    public boolean isLikedByMe(Long postId) {
        User user = getCurrentUser();
        return likeRepository.findByUserIdAndPostId(user.getId(), postId).isPresent();
    }
}
