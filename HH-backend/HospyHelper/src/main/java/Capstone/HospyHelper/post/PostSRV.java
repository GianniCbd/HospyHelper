package Capstone.HospyHelper.post;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.auth.UserDAO;
import Capstone.HospyHelper.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostSRV {

    @Autowired
    PostDAO postDAO;
    @Autowired
    UserDAO userDAO;

    public Page<Post> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return postDAO.findAll(pageable);
    }
    public PostResponseDTO savePost(PostDTO postDTO, User user) {
        User loadedUser = userDAO.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found"));

        Post post = new Post(
                postDTO.title(),
                postDTO.content(),
                loadedUser);
        postDAO.save(post);
        PostResponseDTO responseDTO = new PostResponseDTO(post.getTitle(), postDTO.content(),loadedUser);
        return responseDTO;
    }
    public Post getPostById(Long id) {
        return postDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public Post updatePost(Long id, PostDTO postDTO) {
        Post existingPost = postDAO.findById(id).orElseThrow(() -> new NotFoundException("Room not found with ID: " + id));
        existingPost.setTitle(postDTO.title());
        existingPost.setContent(postDTO.content());


        return postDAO.save(existingPost);
    }
    public void deletePost(Long id) {
        Post post = this.getPostById(id);
        postDAO.delete(post);
    }

    //******************************************************************************************

    public List<PostDTO> findByTitle(String title) {
        List<Post> posts = postDAO.findByTitle(title);
        return convertToDTOList(posts);
    }
    public List<PostDTO> findAllOrderByLikesDesc() {
        List<Post> posts = postDAO.findAllOrderByLikesDesc();
        return convertToDTOList(posts);
    }
    public List<PostDTO> findRecentPosts(LocalDateTime startDate) {
        List<Post> posts = postDAO.findRecentPosts(startDate);
        return convertToDTOList(posts);
    }

    public User findUserById(UUID userId) {
        return userDAO.findById(userId).orElse(null);
    }

    @Transactional
    public void incrementViews(long postId) {
        postDAO.incrementViews(postId);
    }

    @Transactional
    public void incrementLikes(long postId) {
        postDAO.incrementLikes(postId);
    }
    @Transactional
    public void decrementLikes(long postId) {
        postDAO.decrementLikes(postId);
    }

    @Transactional
    public void incrementShares(long postId) {
        postDAO.incrementShares(postId);
    }


    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getTitle(),
                post.getContent()

        );
    }
    private List<PostDTO> convertToDTOList(List<Post> posts) {
        return posts.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
