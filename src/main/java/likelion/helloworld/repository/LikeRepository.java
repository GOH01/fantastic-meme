package likelion.helloworld.repository;

import jakarta.persistence.EntityManager;
import likelion.helloworld.domain.Article;
import likelion.helloworld.domain.Like;
import likelion.helloworld.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final EntityManager em;

    public void saveLike(Like like){em.persist(like);}
    public void deleteLike(Like like){em.remove(like);}

    public List<Like> findArticleLike(Article article){
        return em.createQuery("select l from likes l where l.article=:a", Like.class)
                .setParameter("a",article).getResultList();
    }

    public List<Like> findMemberLike(Member member){
        return em.createQuery("select l from likes l where l.user=:m", Like.class)
                .setParameter("m",member).getResultList();
    }

    public List<Article> findMemberLikeArticle(Member member){
        return em.createQuery("select l.article from likes l where l.user=:m", Article.class)
                .setParameter("m",member).getResultList();
    }

    public List<Article> findAllArticleOrderByLikeCountDesc(){
        return em.createQuery("select a from Article a order by a.likescount DESC",Article.class).getResultList();
    }
    
}
