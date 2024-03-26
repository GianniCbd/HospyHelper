package Capstone.HospyHelper.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostDAO extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.title = :title")
    List<Post> findByTitle(@Param("title") String title);
    @Query("SELECT p FROM Post p ORDER BY p.likes DESC")
    List<Post> findAllOrderByLikesDesc();
    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.id = :postId")
    void incrementViews(@Param("postId") long postId);

    @Modifying
    @Query("UPDATE Post p SET p.likes = p.likes + 1 WHERE p.id = :postId")
    void incrementLikes(@Param("postId") long postId);

    @Modifying
    @Query("UPDATE Post p SET p.shares = p.shares + 1 WHERE p.id = :postId")
    void incrementShares(@Param("postId") long postId);

    @Query("SELECT p FROM Post p WHERE p.creationDate >= :startDate")
    List<Post> findRecentPosts(@Param("startDate") LocalDateTime startDate);
}
