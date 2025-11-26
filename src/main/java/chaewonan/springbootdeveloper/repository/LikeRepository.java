package chaewonan.springbootdeveloper.repository;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.domain.Like;
import chaewonan.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Object> findByUserAndPost(User user, Article article);
}
