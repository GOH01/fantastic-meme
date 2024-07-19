package likelion.helloworld.controller;

import likelion.helloworld.DTO.*;
import likelion.helloworld.DTO.LikeDTO.*;
import likelion.helloworld.domain.*;
import likelion.helloworld.service.*;
import likelion.helloworld.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final MemberService memberService;
    private final ArticleService articleService;
    private final JwtUtility jwtUtility;

    @PostMapping("/Like")
    public LikeResponse createAndDeleteLike(@RequestBody LikeCreateAndDeleteRequest request, @RequestHeader("Authorization")String token){
        String userToken= jwtUtility.bearerToken(token);
        Like like=likeService.addLike(userToken, request.getArticleId());
        return new LikeResponse(like);
    }

    @GetMapping("/Like/userlike")
    public List<ArticleDTO.ResponseArticle> userLikeArticle(@RequestHeader("Authorization") String token){
        List<ArticleDTO.ResponseArticle> response=new ArrayList<>();
        String userToken= jwtUtility.bearerToken(token);
        Member member=memberService.tokenToMember(userToken);
        for(Article article : likeService.getUserLikeArticle(member.getUserId())){
            response.add(new ArticleDTO.ResponseArticle(article,true));
        }
        return response;
    }

    @GetMapping("/Like/AllArticleDesc")
    public List<ArticleDTO.ResponseArticle> likeAllArticleDesc(@RequestHeader("Authorization") String token){
        List<ArticleDTO.ResponseArticle> response=new ArrayList<>();
        String userToken=jwtUtility.bearerToken(token);
        for(Article article : likeService.getLikeDescendingOrder()){
            boolean likedbyuser= userToken != null && articleService.isArticleLikedByUser(userToken, article.getId());
            response.add(new ArticleDTO.ResponseArticle(article,likedbyuser));
        }
        return response;
    }
}
