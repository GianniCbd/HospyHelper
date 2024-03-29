package Capstone.HospyHelper.post;


import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.auth.UserSRV;
import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostCTRL {

    @Autowired
    PostSRV postSRV;

    @Autowired
    UserSRV userSRV;

    @GetMapping
    public Page<Post> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "title") String orderBy) {
        return postSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDTO savePost(@RequestBody @Validated PostDTO postDTO,  @AuthenticationPrincipal User currentAuthenticatedUser, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.postSRV.savePost(postDTO,currentAuthenticatedUser);
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

    @PutMapping("/{postId}/increment-likes")
    public ResponseEntity<Void> incrementLikes( @PathVariable long postId) {
        postSRV.incrementLikes(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{postId}/decrement-likes")
    public ResponseEntity<Void> decrementLikes(@PathVariable long postId) {
        postSRV.decrementLikes(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{postId}/increment-shares")
    public ResponseEntity<Void> incrementShares(@PathVariable long postId) {
        postSRV.incrementShares(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
