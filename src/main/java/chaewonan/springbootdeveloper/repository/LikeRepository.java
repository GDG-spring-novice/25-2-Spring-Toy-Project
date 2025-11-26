package chaewonan.springbootdeveloper.repository;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.domain.Like;
import chaewonan.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 특정 사용자가 특정 게시글을 좋아요 눌렀는지 확인
    Optional<Like> findByPostAndUser(Article post, User user);

    // 게시글의 좋아요 개수
    int countByPostId(Long postId);
}
