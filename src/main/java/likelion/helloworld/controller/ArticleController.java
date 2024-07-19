package likelion.helloworld.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import likelion.helloworld.DTO.ArticleDTO.*;
import likelion.helloworld.domain.Article;
import likelion.helloworld.domain.Member;
import likelion.helloworld.service.ArticleService;
import likelion.helloworld.service.JwtUtility;
import likelion.helloworld.service.LikeService;
import likelion.helloworld.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final JwtUtility jwtUtility;



    @GetMapping("/article/{id}")
    public ResponseArticle getArticle(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        Article article=articleService.findArticle(id);
        String userToken=jwtUtility.bearerToken(token);
        boolean likedbyuser = ( userToken != null ) && articleService.isArticleLikedByUser(userToken, id);
        return new ResponseArticle(article, likedbyuser);
    }

    @PostMapping("/article/add")
    public ResponseArticle createArticle(@RequestBody RequestArticle request){
        String userId=jwtUtility.validateToken(request.getToken()).getSubject();
        Article article=articleService.saveNewArticle(userId, request.getTitle(), request.getContent());
        return new ResponseArticle(article, false);
    }


    @Operation(summary = "게시글 수정", description ="작성자의 token과 함께 수정할 제목과 내용을 입력", tags = {"article"},
        responses = {@ApiResponse(responseCode = "200", description = "성공적인 수정"),
                    @ApiResponse(responseCode = "500", description = "미안 오류 처리 안했어...")})
    @PutMapping("/article/{id}")
    public ResponseArticle updateArticle(@RequestBody RequestArticle request, @Parameter(description = "게시글 번호", example = "1") @PathVariable("id") Long id){
        Article article =articleService.updateArticle(id,request.getTitle(), request.getContent(), request.getToken());
        return new ResponseArticle(article, false);
    }

    @DeleteMapping("/article/{id}")
    public void deleteArticle(@RequestBody RemoveArticle request, @PathVariable("id") Long id){
        articleService.deleteArticle(id, request.getToken());
    }

    @GetMapping("/articles/all")
    public List<ResponseArticle> allArticleList(@RequestHeader("Authorization") String token){
        List<ResponseArticle> responseArticles=new ArrayList<>();
        String userToken=jwtUtility.bearerToken(token);
        for(Article article : articleService.findAllArticle()){
            boolean likedbyuser= userToken != null && articleService.isArticleLikedByUser(userToken, article.getId());
            responseArticles.add(new ResponseArticle(article, likedbyuser));
        }
        return responseArticles;
    }

    @GetMapping("/articles/all/{member}")
    public List<ResponseArticle> writerArticleList(@PathVariable("member") String memberId,@RequestHeader("Authorization") String token){
        List<ResponseArticle> responseArticles=new ArrayList<>();
        String userToken= jwtUtility.bearerToken(token);
        for(Article article : articleService.findUserArticle(memberId)){
            boolean likedbyuser= userToken != null && articleService.isArticleLikedByUser(userToken, article.getId());
            responseArticles.add(new ResponseArticle(article, likedbyuser));
        }
        return responseArticles;
    }

}
