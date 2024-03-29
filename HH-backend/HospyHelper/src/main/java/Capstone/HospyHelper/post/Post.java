package Capstone.HospyHelper.post;

import Capstone.HospyHelper.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "likes", nullable = false)
    private int likes;
    @Column(name = "views", nullable = false)
    private int views;
    @Column(name = "shares", nullable = false)
    private int shares;

    @ManyToOne

    @JoinColumn(name = "author_id", nullable = false)
    private User user;


    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.creationDate = LocalDateTime.now();
        this.likes = 0;
        this.views = 0;
        this.shares = 0;
    }
}
