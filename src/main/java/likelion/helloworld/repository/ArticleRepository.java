package likelion.helloworld.repository;

import likelion.helloworld.domain.Article;

import java.util.List;

public interface ArticleRepository {
    public Article saveNewArticle(Article article);
    public void deleteArticle(Article article);
    public Article findById(Long articleId);
    public List<Article> findAll();
    public List<Article> findUserAll(Long memberId);
    public void updateLikeCount(Article article, boolean b);
}
