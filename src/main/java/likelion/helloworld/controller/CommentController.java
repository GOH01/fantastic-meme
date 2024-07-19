package likelion.helloworld.controller;

import likelion.helloworld.domain.*;
import likelion.helloworld.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import likelion.helloworld.DTO.CommentDTO.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public CommentResponse createComment(@RequestBody CommentCreateRequest request){
        Comment comment=commentService.saveComment(request.getToken(), request.getArticleId(), request.getContent());
        return new CommentResponse(comment);
    }

    @PutMapping("/comment")
    public CommentResponse updateComment(@RequestBody CommentUpdateRequest request){
        Comment comment=commentService.updateComment(request.getCommentId(), request.getToken(), request.getContent());
        return new CommentResponse(comment);
    }

    @DeleteMapping("/comment")
    public void deleteComment(@RequestBody CommentDeleteRequest request){
        commentService.deleteComment(request.getCommentId(), request.getToken());
    }

    @GetMapping("/comment/article/{id}")
    public List<CommentResponse> articleComment(@PathVariable("id") Long article){
        List<CommentResponse> response= new ArrayList<>();
        for(Comment comment : commentService.articleToComment(article)){
            response.add(new CommentResponse(comment));
        }
        return response;
    }
}
