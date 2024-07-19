package likelion.helloworld.DTO;

import likelion.helloworld.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

public class CommentDTO {
    @Data
    public static class CommentCreateRequest{
        private String token;
        private String content;
        private long articleId;
    }

    @Data
    public static class CommentUpdateRequest{
        private String token;
        private long commentId;
        private String content;

    }

    @Data
    public static class CommentDeleteRequest{
        private String token;
        private long commentId;
    }

    @Data
    public static class CommentResponse {
        private String content;
        private LocalDateTime createDate;
        private boolean isUpdate;
        private String writer;
        private String writer_id;

        public CommentResponse(Comment comment){
            this.content= comment.getContent();
            this.createDate=comment.getCreateDate();
            this.writer=comment.getWriter().getNickname();
            this.writer_id=comment.getWriter().getUserId();

            if(comment.getCreateDate().equals(comment.getUpdateDate())){
                this.isUpdate=false;
            }
            else{
                this.isUpdate=true;
            }
        }
    }
}
