//package chaewonan.springbootdeveloper.controller;
//
//import chaewonan.springbootdeveloper.service.LikeService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/likes")
//public class LikeApiController {
//
//    private final LikeService likeService;
//
//    // 좋아요 추가
//    @PostMapping("/{postId}")
//    public ResponseEntity<Void> like(@PathVariable Long postId) {
//        likeService.like(postId);
//        return ResponseEntity.ok().build();
//    }
//
//    // 좋아요 취소
//    @DeleteMapping("/{postId}")
//    public ResponseEntity<Void> unlike(@PathVariable Long postId) {
//        likeService.unlike(postId);
//        return ResponseEntity.ok().build();
//    }
//}
