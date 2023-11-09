import com.kyamran.app.model.Post;
import com.kyamran.app.repository.PostRepository;
import com.kyamran.app.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PostServiceTest {
    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    private Post post;

    @BeforeEach
    public void setUp()  {
        MockitoAnnotations.openMocks(this);
        post = new Post("Test content", 1L);
    }

    @Test
    public void saveTest()  {
        when(postRepository.save(post)).thenReturn(post);
        postService.save(post);

        verify(postRepository,times(1)).save(post);
    }

    @Test
    public void updateTest()  {
        when(postRepository.update(post)).thenReturn(post);
        postService.update(post);

        verify(postRepository,times(1)).update(post);
    }

    @Test
    public void getByIdTest()  {
        when(postRepository.getById(1L)).thenReturn(post);
        postService.getById(1L);

        verify(postRepository,times(1)).getById(1L);
    }

    @Test
    public void deleteByIdTest()  {
        doNothing().when(postRepository).deleteById(1L);
        postService.deleteById(1L);

        verify(postRepository,times(1)).deleteById(1L);
    }

    @Test
    public void getAllTest()  {
        List<Post> posts = new ArrayList<>();
        posts.add(post);

        when(postRepository.getAll()).thenReturn(posts);
        postService.getAll();

        verify(postRepository,times(1)).getAll();
    }
}
