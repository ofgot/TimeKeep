package sir.timekeep.rest;

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

@RestController
@RequestMapping("/rest")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("!anonymous && (hasRole('USUAL') || hasRole('PREMIUM'))")
    @GetMapping("/{id}/memories")
    public ResponseEntity<List<Memory>> getCreatorsMemories(@PathVariable Integer id) {
        List<Memory> memories = postService.findMemoriesByCreator(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Memories not found"));
        return ResponseEntity.ok(memories);
    }

    @PreAuthorize("!anonymous && (hasRole('USUAL') || hasRole('PREMIUM'))")
    @GetMapping("/{id}/capsules")
    public ResponseEntity<List<Capsule>> getCreatorsCapsules(@PathVariable Integer id) {
        List<Capsule> capsules = postService.findOpenCapsulesByCreator(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Capsules not found"));
        return ResponseEntity.ok(capsules);
    }

    @PreAuthorize("!anonymous && (hasRole('USUAL') || hasRole('PREMIUM'))")
    @GetMapping("/{id}/all")
    public ResponseEntity<List<Post>> getAllCreatorsPosts(@PathVariable Integer id) {
        List<Post> posts = postService.findAllByCreator(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Posts not found"));
        return ResponseEntity.ok(posts);
    }

    // works
    @PreAuthorize("!anonymous && (hasRole('USUAL') || hasRole('PREMIUM'))")
    @PostMapping(value = "/{id}/memory", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMemory(@PathVariable Integer id, @RequestBody Memory post) {
        if (!post.getPostCreator().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot create a post.");
        }
        postService.create(post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // works
    @PreAuthorize("!anonymous && (hasRole('USUAL') || hasRole('PREMIUM'))")
    @PutMapping("/{id}/memory")
    public ResponseEntity<Post> updateMemory(@PathVariable Integer id, @RequestBody Memory memory) {
        if (!memory.getPostCreator().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot update this memory.");
        }
        Memory existingMemory = postService.findById(id);
        if (existingMemory == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Memory not found");
        }

        existingMemory.setName(memory.getName());
        existingMemory.setDescription(memory.getDescription());

        Post updatedMemory = postService.update(existingMemory);
        return ResponseEntity.ok(updatedMemory);
    }

    //works
    @PreAuthorize("!anonymous && (hasRole('USUAL') || hasRole('PREMIUM'))")
    @PostMapping("/{id}/capsule")
    public ResponseEntity<Void> createCapsule(@PathVariable Integer id, @RequestBody Capsule post) {
        if (!post.getPostCreator().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot create a post.");
        }
        postService.create(post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //works
    @PreAuthorize("!anonymous && (hasRole('USUAL') || hasRole('PREMIUM'))")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> removePost(@PathVariable Integer id) {
        Post post = postService.findPostById(id);

        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        postService.remove(post);
        return ResponseEntity.ok().build();
    }

}
