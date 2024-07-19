package likelion.helloworld.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.helloworld.domain.Article;
import likelion.helloworld.domain.Member;
import lombok.Data;

import java.time.LocalDateTime;

public class ArticleDTO {
    @Data
    public static class ResponseArticle{
        private String title;
        private String content;
        private String writer;
        private LocalDateTime createDate;
        private boolean isChange;
        private int likescount;
        private boolean likedbyuser;

        public ResponseArticle(Article article, boolean likedbyuser){
            this.title=article.getTitle();
            this.content=article.getContent();
            this.writer=article.getWriter().getNickname();
            this.createDate=article.getCreateDate();

            this.likescount=article.getLikescount();
            this.likedbyuser=likedbyuser;


            if(article.getCreateDate().equals(article.getUpdateDate())){
                this.isChange=false;
            }
            else{
                this.isChange=true;
            }
        }
    }
    @Data
    public static class RequestArticle{
        @Schema(description = "게시글 제목", example = "스프링 공부중...")
        private String title;
        @Schema(description = "게시글 내용", example = "JPA 너무 어렵다 졸업할 수 있을까")
        private String content;
        @Schema(description = "로그인 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJzaWIiOiJ0ZX")
        private String token;
    }

    @Data
    public static class RemoveArticle{
        private String token;
    }

    @Data
    public static  class CheckArticle{
        private String token;
    }

}
