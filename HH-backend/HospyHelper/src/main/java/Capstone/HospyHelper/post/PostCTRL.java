package Capstone.HospyHelper.post;


import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostCTRL {

    @Autowired
    PostSRV postSRV;

    @GetMapping
    public Page<Post> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "title") String orderBy) {
        return postSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping("/save/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> savePost(@RequestBody @Validated PostDTO postDTO, @PathVariable UUID userId, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Post savedPost = postSRV.savePost(postDTO, userId);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Long id) {
        return this.postSRV.getPostById(id);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        return postSRV.updatePost(id,postDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postSRV.deletePost(id);
    }


    //***********************************************************************************

    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPostsByTitle(@RequestParam String title) {
        List<PostDTO> posts = postSRV.findByTitle(title);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/likes-desc")
    public ResponseEntity<List<PostDTO>> getAllPostsOrderByLikesDesc() {
        List<PostDTO> posts = postSRV.findAllOrderByLikesDesc();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PostDTO>> getRecentPosts() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        List<PostDTO> posts = postSRV.findRecentPosts(startDate);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/{postId}/increment-views")
    public ResponseEntity<Void> incrementViews(@PathVariable long postId) {
        postSRV.incrementViews(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
