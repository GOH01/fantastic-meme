package likelion.helloworld.service;

import likelion.helloworld.domain.Article;
import likelion.helloworld.domain.Member;
import likelion.helloworld.repository.ArticleRepository;
import likelion.helloworld.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;
    private final LikeRepository likeRepository;


    @Transactional
    public Article saveNewArticle(String writerId, String title, String content){
        Member member = memberService.findByUserId(writerId);
        Article article =new Article(title, content, member);
        articleRepository.saveNewArticle(article);
        return article;
    }

    @Transactional
    public Article updateArticle(Long articleId, String title,String content, String token){
        Article article= articleRepository.findById(articleId);
        Member member=memberService.tokenToMember(token);
        if(member==article.getWriter()){
            article.update(title,content);
        }
        return article;
    }

    @Transactional
    public void deleteArticle(Long articleId, String token){
        Article article =articleRepository.findById(articleId);
        Member member=memberService.tokenToMember(token);
        if(member==article.getWriter()){
            articleRepository.deleteArticle(article);
        }
    }

    public Article findArticle(Long articleId){
        return articleRepository.findById(articleId);
    }

    public List<Article> findAllArticle(){
        return articleRepository.findAll();
    }

    public List<Article> findUserArticle(String memberId){
        Member member=memberService.findByUserId(memberId);
        return articleRepository.findUserAll(member.getId());
    }

    @Transactional
    public boolean isArticleLikedByUser(String token, Long articleId){
        Article article = findArticle(articleId);
        Member member=memberService.tokenToMember(token);
        return likeRepository.findMemberLikeArticle(member).contains(article);
    }

}
