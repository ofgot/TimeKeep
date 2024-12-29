package sir.timekeep.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sir.timekeep.model.Capsule;
import sir.timekeep.model.Memory;
import sir.timekeep.model.Post;
import sir.timekeep.service.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class PostController {
    private static final Logger LOG = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("#id == authentication.principal.user.id")
    @GetMapping("/{id}/memories")
    public ResponseEntity<List<Memory>> getCreatorsMemories(@PathVariable Integer id) {
        List<Memory> memories = postService.findMemoriesByCreator(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Memories not found"));
        return ResponseEntity.ok(memories);
    }

    @PreAuthorize("#id == authentication.principal.user.id")
    @GetMapping("/{id}/capsules")
    public ResponseEntity<List<Capsule>> getCreatorsCapsules(@PathVariable Integer id) {
        List<Capsule> capsules = postService.findOpenCapsulesByCreator(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Capsules not found"));
        return ResponseEntity.ok(capsules);
    }

    @PreAuthorize("#id == authentication.principal.user.id")
    @GetMapping("/{id}/all")
    public ResponseEntity<List<Post>> getAllCreatorsPosts(@PathVariable Integer id) {
        List<Post> posts = postService.findAllByCreator(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Posts not found"));
        return ResponseEntity.ok(posts);
    }

    /*
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    private Optional<List<Post>> findAll(){
        return postService.findAll();
    }
     */

    @PreAuthorize("#id == authentication.principal.user.id")
    @PostMapping("/{id}/memory")
    public ResponseEntity<Void> createMemory(@PathVariable Integer id, @RequestBody Memory post) {
        if (!post.getPostCreator().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot allow to create a post.");
        }
        postService.create(post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("#id == authentication.principal.user.id")
    @PostMapping("/{id}/capsule")
    public ResponseEntity<Void> createCapsule(@PathVariable Integer id, @RequestBody Capsule post) {
        if (!post.getPostCreator().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot allow to create a post.");
        }
        postService.create(post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("#post.postCreator.id == #id")
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer id, @RequestBody Post post) {
        Post updatedPost = postService.update(post);
        return ResponseEntity.ok(updatedPost);
    }

    @PreAuthorize("#post.postCreator.id == #id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id, @RequestBody Post post) {
        postService.remove(post);
        return ResponseEntity.noContent().build();
    }
}
