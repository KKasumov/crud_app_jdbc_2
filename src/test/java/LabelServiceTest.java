import com.kyamran.app.model.Label;
import com.kyamran.app.repository.LabelRepository;
import com.kyamran.app.service.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class LabelServiceTest {
    @Mock
    LabelRepository labelRepository;

    @InjectMocks
    LabelService labelService;

    private Label label;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        label = new Label(1L, "Test content");
    }

    @Test
    public void saveTest() {
        when(labelRepository.save(label)).thenReturn(label);
        labelService.save(label);

        verify(labelRepository,times(1)).save(label);
    }

    @Test
    public void updateTest() {
        when(labelRepository.update(label)).thenReturn(label);
        labelService.update(label);

        verify(labelRepository,times(1)).update(label);
    }

    @Test
    public void getByIdTest() {
        when(labelRepository.getById(1L)).thenReturn(label);
        labelService.getById(1L);

        verify(labelRepository,times(1)).getById(1L);
    }

    @Test
    public void deleteByIdTest() {
        doNothing().when(labelRepository).deleteById(1L);
        labelService.deleteById(1L);

        verify(labelRepository,times(1)).deleteById(1L);
    }

    @Test
    public void getAllTest() {
        List<Label> labels = new ArrayList<>();
        labels.add(label);

        when(labelRepository.getAll()).thenReturn(labels);
        labelService.getAll();

        verify(labelRepository,times(1)).getAll();
    }
}
