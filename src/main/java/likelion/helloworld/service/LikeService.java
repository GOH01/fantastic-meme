package likelion.helloworld.service;

import likelion.helloworld.domain.Article;
import likelion.helloworld.domain.Like;
import likelion.helloworld.domain.Member;
import likelion.helloworld.repository.ArticleRepository;
import likelion.helloworld.repository.LikeRepository;
import likelion.helloworld.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {


    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final MemberService memberService;
    private final LikeRepository likeRepository;

    @Transactional
    public Like addLike(String token, Long article_id){
        Article article = articleService.findArticle(article_id);
        Member member= memberService.tokenToMember(token);
        List<Article> existLikes= likeRepository.findMemberLikeArticle(member);
        List<Like> existArticleLikes=likeRepository.findArticleLike(article);
        List<Like> existMemberLikes=likeRepository.findMemberLike(member);

        Like likeRemove = existArticleLikes.stream()
                .filter(like->existMemberLikes.contains(like))
                .findFirst()
                .orElse(null);
        if(!existLikes.contains(article)){
            Like like=new Like(memberService.tokenToMember(token),article);
            likeRepository.saveLike(like);
            articleRepository.updateLikeCount(article, true);
            return like;
        }
        else{
            likeRepository.deleteLike(likeRemove);
            articleRepository.updateLikeCount(article, false);
            return null;
        }
    }

//    @Transactional
//    public int getArticleLikeCount(Long articleId){
//        Article article=articleService.findArticle(articleId);
//        return likeRepository.findArticleLike(article).size();
//    }



    public List<Article> getUserLikeArticle(String userId){
        Member member=memberService.findByUserId(userId);
        return likeRepository.findMemberLikeArticle(member);
    }

    public List<Article> getLikeDescendingOrder(){
        return likeRepository.findAllArticleOrderByLikeCountDesc();
    }



}
