package likelion.helloworld.DTO;

import likelion.helloworld.domain.Like;
import lombok.Data;

public class LikeDTO {

    @Data
    public static class LikeCreateAndDeleteRequest{
        private String token;
        private long articleId;
    }

    @Data
    public static class FindUserLikeArticle{
        private String userId;
    }

    @Data
    public static class LikeResponse{
        private long userId;
        private long articleId;
        private boolean isLiked;

        public LikeResponse(Like like){
            if (like != null){
                this.userId=like.getUser().getId();
                this.articleId=like.getArticle().getId();
                this.isLiked=true;
            }else{
                this.isLiked=false;
            }

        }
    }
}
