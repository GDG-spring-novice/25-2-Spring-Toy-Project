package chaewonan.springbootdeveloper.service;

import chaewonan.springbootdeveloper.domain.Article;
import chaewonan.springbootdeveloper.domain.ArticleImage;
import chaewonan.springbootdeveloper.repository.ArticleImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ArticleImageRepository imageRepository;
    private final String uploadPath = "uploads/";

    public void saveImages(List<MultipartFile> images, Article article) {
        for (MultipartFile file : images) {
            String original = file.getOriginalFilename();
            String ext = original.substring(original.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + ext;
            File dest = new File(uploadPath + filename);
            dest.getParentFile().mkdirs();

            try {
                file.transferTo(dest);
            } catch (IOException e) {
                throw new RuntimeException("이미지 저장 실패", e);
            }

            ArticleImage image = new ArticleImage("/uploads/" + filename);
            article.addImage(image);
            imageRepository.save(image);
        }
    }
}
