package Capstone.HospyHelper.review;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.auth.UserDAO;
import Capstone.HospyHelper.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public ReviewResponseDTO saveReview(ReviewDTO reviewDTO, User user) {
        User loadedUser = userDAO.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found"));

        int rating = reviewDTO.rating();
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Il rating deve essere compreso tra 1 e 5.");
        }

        Review review = new Review(
                rating,
                reviewDTO.comment(),
                loadedUser);
        reviewDAO.save(review);
        ReviewResponseDTO responseDTO = new ReviewResponseDTO(
                review.getRating(),
                review.getComment(),
                loadedUser
        );
        return responseDTO;
    }
    public Review getReviewById(Long id) {
        return reviewDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public Review updateReview(Long id, ReviewDTO reviewDTO) {
        Review existingReview = reviewDAO.findById(id).orElseThrow(() -> new NotFoundException("Review not found with ID: " + id));


        existingReview.setRating(reviewDTO.rating());
        existingReview.setComment(reviewDTO.comment());
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
                .map(review -> new ReviewDTO(review.getRating(), review.getComment(),review.getUser()))
                .collect(Collectors.toList());
    }

}
