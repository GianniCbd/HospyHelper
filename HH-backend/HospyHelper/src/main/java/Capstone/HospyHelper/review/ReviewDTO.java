package Capstone.HospyHelper.review;

import Capstone.HospyHelper.auth.User;

public record ReviewDTO(
        int rating,
        String comment,
        User user

) {
}
