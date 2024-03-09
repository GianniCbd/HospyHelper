package Capstone.HospyHelper.review;

import Capstone.HospyHelper.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "Review")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Review {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id",nullable = false)
        private Long id;
        private int rating;
        private String comment;


        @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
        @JsonIgnore
        @JoinColumn(name = "user_id")
        private User user;

    public Review(int rating, String comment,User user) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
    }
}

