package chaewonan.springbootdeveloper.dto;

import lombok.Getter;

@Getter
public class LikeRequest {

    private Long postId;
    private Long userId;  // 로그인 정보에서 가져오는 값
}
