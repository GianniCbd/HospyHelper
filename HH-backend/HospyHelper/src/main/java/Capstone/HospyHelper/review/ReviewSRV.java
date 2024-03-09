package Capstone.HospyHelper.review;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.auth.UserDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewSRV {

    @Autowired
    ReviewDAO reviewDAO;
    @Autowired
    UserDAO userDAO;

    public Page<Review> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return reviewDAO.findAll(pageable);
    }
    public Review saveReview(ReviewDTO reviewDTO, UUID userId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        Review review = new Review(
                reviewDTO.rating(),
                reviewDTO.comment(),
                user);
        return reviewDAO.save(review);
    }
    public Review getReviewById(Long id) {
        return reviewDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public Review updateReview(Long id, ReviewDTO rv) {
        Review existingReview = reviewDAO.findById(id).orElseThrow(() -> new NotFoundException("Review not found with ID: " + id));
        existingReview.setRating(rv.rating());
        existingReview.setComment(rv.comment());
        return reviewDAO.save(existingReview);
    }
    public void deleteReview(Long id) {
        Review review = this.getReviewById(id);
        reviewDAO.delete(review);
    }

    //*************************************************************************

    public List<ReviewDTO> findReviewsWithRatingGreaterThan(int rating) {
        List<Review> reviews = reviewDAO.findReviewsWithRatingGreaterThan(rating);
        return reviews.stream()
                .map(review -> new ReviewDTO(review.getRating(), review.getComment()))
                .collect(Collectors.toList());
    }

}
