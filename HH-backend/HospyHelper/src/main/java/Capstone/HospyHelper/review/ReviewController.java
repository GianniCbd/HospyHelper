package Capstone.HospyHelper.review;

import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewSRV reviewSRV;

    @GetMapping
    public Page<Review> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "10") int pageSize,
                               @RequestParam(defaultValue = "name") String orderBy) {
        return reviewSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping("/save/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Review> saveEmployee(@RequestBody @Validated ReviewDTO reviewDTO, @PathVariable UUID userId, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Review savedReview = reviewSRV.saveReview(reviewDTO, userId);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable Long id) {
        return this.reviewSRV.getReviewById(id);
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        return reviewSRV.updateReview(id,reviewDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewSRV.deleteReview(id);
    }

    //*************************************************************
    @GetMapping("/findByRatingGreaterThan")
    public List<ReviewDTO> findReviewsWithRatingGreaterThan(@RequestParam int rating) {
        return reviewSRV.findReviewsWithRatingGreaterThan(rating);
    }
}