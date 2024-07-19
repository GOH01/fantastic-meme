package likelion.helloworld.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name="likes")
@NoArgsConstructor
@Getter
public class Like {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    //@OnDelete(action= OnDeleteAction.CASCADE)
    private Member user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    public Like(Member user, Article article ){
        this.user=user;
        this.article=article;
    }
}
