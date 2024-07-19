package likelion.helloworld.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Article {
    @Id @GeneratedValue
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="writer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member writer;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    private int likescount = 0;

    public Article(String title, String content, Member writer){
        this.title=title;
        this.content=content;
        this.writer=writer;
        this.createDate=LocalDateTime.now();
        this.updateDate=this.createDate;

    }
    public void update(String title, String content){
        this.title=title;
        this.content=content;
        this.updateDate=LocalDateTime.now();
    }

}
