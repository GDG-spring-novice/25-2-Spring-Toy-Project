package chaewonan.springbootdeveloper.repository;

import chaewonan.springbootdeveloper.domain.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
}
