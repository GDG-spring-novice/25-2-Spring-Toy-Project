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
    private final BlogRepository blogRepository; // ArticleRepository 역할

    /**
     * 좋아요 추가
     */
    @Transactional
    public void like(Long postId, String userEmail) {

        // 로그인한 사용자 조회
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 게시글 조회
        Article article = blogRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        // 이미 좋아요 되어 있는지 체크
        if (likeRepository.findByUserAndPost(user, article).isPresent()) {
            return; // 이미 좋아요 누른 상태라면 무시 (또는 예외 던져도 됨)
        }

        // 좋아요 저장
        Like like = Like.builder()
                .user(user)
                .post(article)
                .build();

        likeRepository.save(like);
    }

    /**
     * 좋아요 취소
     */
    @Transactional
    public void unlike(Long postId, String userEmail) {

        // 로그인한 사용자 조회
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 게시글 조회
        Article article = blogRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        // 좋아요 정보 조회
        Like like = likeRepository.findByUserAndPost(user, article)
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));

        // 좋아요 삭제
        likeRepository.delete(like);
    }

    /**
     * 게시글 좋아요 개수 조회
     */
    @Transactional(readOnly = true)
    public int countLikes(Long postId) {

        Article article = blogRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        return likeRepository.countByPost(article);
    }
}
