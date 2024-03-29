package Capstone.HospyHelper.post;

import Capstone.HospyHelper.auth.User;

public record PostResponseDTO(String title,
                              String content,
                              User user
) {
}
