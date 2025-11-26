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

    // 좋아요 추가
    public void like(Long postId) {

        // 현재 로그인 사용자 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + username));

        // 좋아요 대상 게시글
        Article article = blogRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found: " + postId));

        // 이미 좋아요 눌렀는지 확인
        boolean exists = likeRepository.findByUserAndPost(user, article).isPresent();
        if (exists) return; // 이미 눌렀으면 무시

        // 좋아요 저장
        Like like = Like.builder()
                .post(article)
                .user(user)
                .build();

        likeRepository.save(like);
    }

    // 좋아요 취소
    public void unlike(Long postId) {

        // 현재 로그인 사용자
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + username));

        // 게시글 가져오기
        Article article = blogRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found: " + postId));

        // 챙기기 (투명 찾기)
        Like like = likeRepository.findByUserAndPost(user, article)
                .orElseThrow(() -> new IllegalArgumentException("like not found"));

        likeRepository.delete(like);
    }

    public boolean isLikedByMe(Long postId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found: " + username));

        Article article = blogRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found: " + postId));

        return likeRepository.findByUserAndPost(user, article).isPresent();
    }
}
