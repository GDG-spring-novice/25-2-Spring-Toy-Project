package chaewonan.springbootdeveloper.repository;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.domain.Like;
import chaewonan.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
    //LikeService 에서 Like 로 받는 부분에서 타입이 일치하려면 Optional<Object>가 아닌 Like로 반환 타입 고정
}
