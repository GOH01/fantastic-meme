package likelion.helloworld.service;

import likelion.helloworld.domain.Article;
import likelion.helloworld.domain.Comment;
import likelion.helloworld.domain.Member;
import likelion.helloworld.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final ArticleService articleService;

    @Transactional
    public Comment saveComment(String token, Long article_id, String content){
        Article article=articleService.findArticle(article_id);
        Comment comment=new Comment(memberService.tokenToMember(token), article, content);
        commentRepository.saveComment(comment);
        return comment;
    }
    public List<Comment> articleToComment(Long articleId){
        Article article=articleService.findArticle(articleId);
        return commentRepository.findArticleComment(article);
    }

    @Transactional
    public boolean deleteComment(Long commentId, String token){
        Comment comment=commentRepository.findById(commentId);
        Member member=memberService.tokenToMember(token);
        if(member==comment.getWriter()){
            commentRepository.deleteComment(comment);
            return true;
        }
        return false;
    }

    @Transactional
    public Comment updateComment(Long commentId, String token, String content){
        Comment comment=commentRepository.findById(commentId);
        Member member=memberService.tokenToMember(token);
        if(member==comment.getWriter()){
            comment.updateComment(content);
        }
        return comment;
    }
}
