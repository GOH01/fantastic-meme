package likelion.helloworld.repository;

import jakarta.persistence.EntityManager;
import likelion.helloworld.domain.Article;
import likelion.helloworld.domain.Comment;
import likelion.helloworld.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Comment findById(Long id){
        return em.find(Comment.class, id);
    }
    public void saveComment(Comment comment){
        em.persist(comment);
    }
    public void deleteComment(Comment comment){
        em.remove(comment);
    }
    public List<Comment> findArticleComment(Article article){
        return em.createQuery("select c from Comment c where c.article=:a", Comment.class)
                .setParameter("a",article).getResultList();
    }
    public List<Comment> findMemberCommnet(Member member){
        return em.createQuery("select c from Comment c where c.writer=:m", Comment.class)
                .setParameter("m",member).getResultList();

    }
    public List<Article> findMemberCommentArticle(Member member){
        return em.createQuery("select DISTINCT c.article from Comment c where c.writer=:w ", Article.class)
                .setParameter("w",member).getResultList();
    }
}
