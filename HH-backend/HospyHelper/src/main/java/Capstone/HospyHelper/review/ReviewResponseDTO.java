package Capstone.HospyHelper.review;

import Capstone.HospyHelper.auth.User;

public record ReviewResponseDTO(int rating,
                                String comment,
                                User user) {
}
