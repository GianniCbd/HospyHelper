package Capstone.HospyHelper.post;

import java.time.LocalDateTime;

public record PostDTO(
        String title,
        String content,
        LocalDateTime creationDate,
        int likes,
        int views,
        int shares

) {
}
