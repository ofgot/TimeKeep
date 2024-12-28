package sir.timekeep.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    // TO DO: PreAuthorization based on login implementation (id of the currently logged user must be equal to id from url)
    @GetMapping(value ="/{id}/memories", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<List<Memory>> getCreatorsMemories(@PathVariable Integer id){
        return postService.findMemoriesByCreator(id);
    }

    // TO DO: PreAuthorization based on login implementation (id of the currently logged user must be equal to id from url)
    @GetMapping(value ="/{id}/capsules", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<List<Capsule>> getCreatorsCapsules(@PathVariable Integer id){
        return postService.findOpenCapsulesByCreator(id);
    }

    // TO DO: PreAuthorization based on login implementation (id of the currently logged user must be equal to id from url)
    @GetMapping(value ="/{id}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<List<Post>> getAllCreatorsPosts(@PathVariable Integer id){
        return postService.findAllByCreator(id);
    }

    /*
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    private Optional<List<Post>> findAll(){
        return postService.findAll();
    }
     */

    @PreAuthorize("(!#post.postCreator.id == id)")
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createPost(@PathVariable Integer id, @RequestBody Post post){
        postService.create(post);
    }

    @PreAuthorize("(!#post.postCreator.id == id)")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Post updatePost(@PathVariable Integer id, @RequestBody Post post){
        return postService.update(post);
    }

    @PreAuthorize("(!#post.postCreator.id == id)")
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void remove(@PathVariable Integer id, @RequestBody Post post){
        postService.remove(post);
    }
}
