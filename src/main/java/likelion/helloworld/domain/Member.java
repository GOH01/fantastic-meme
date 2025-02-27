package likelion.helloworld.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String userId;
    private String password;
    @Setter
    private String nickname;


    public Member(String userId, String password, String nickname) {
        this.userId=userId;
        this.setPassword(password);
        this.nickname=nickname;
    }

    private static final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    public void setPassword(String password){
        this.password=passwordEncoder.encode(password);
    }

    public boolean checkPassword(String rawPassword){
        return passwordEncoder.matches(rawPassword, this.password);
    }

}
