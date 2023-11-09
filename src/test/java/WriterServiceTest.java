import com.kyamran.app.model.Writer;
import com.kyamran.app.repository.WriterRepository;
import com.kyamran.app.service.WriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WriterServiceTest {

    @Mock
    WriterRepository writerRepository;

    @InjectMocks
    WriterService writerService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save() {
        Writer writer = Writer.builder().id(1L).firstName("FirstName").lastName("LastName").build();
        when(writerRepository.save(any(Writer.class))).thenReturn(writer);

        Writer savedWriter = writerService.save(writer);

        assertEquals(writer, savedWriter);
        verify(writerRepository, times(1)).save(writer);
    }


    @Test
    public void update() {
        Writer writer = Writer.builder().id(1L).firstName("FirstName").lastName("LastName").build();
        when(writerRepository.update(any(Writer.class))).thenReturn(writer);

        Writer updatedWriter = writerService.update(writer);

        assertEquals(writer, updatedWriter);
        verify(writerRepository, times(1)).update(writer);
    }

    @Test
    public void getById() {
        Writer writer = Writer.builder().id(1L).firstName("FirstName").lastName("LastName").build();
        when(writerRepository.getById(any(Long.class))).thenReturn(writer);

        Writer retrievedWriter = writerService.getById(1L);

        assertEquals(writer, retrievedWriter);
        verify(writerRepository, times(1)).getById(1L);
    }

    @Test
    public void deleteById() {
        doNothing().when(writerRepository).deleteById(1L);

        writerService.deleteById(1L);

        verify(writerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getAll() {
        Writer writer1 = new Writer();
        writer1.setId(1L);
        writer1.setFirstName("FirstName1");
        writer1.setLastName("LastName1");

        Writer writer2 = new Writer();
        writer2.setId(2L);
        writer2.setFirstName("FirstName2");
        writer2.setLastName("LastName2");

        List<Writer> writers = Arrays.asList(writer1, writer2);

        when(writerRepository.getAll()).thenReturn(writers);

        List<Writer> returnedWriters = writerService.getAll();

        assertEquals(writers, returnedWriters);
        verify(writerRepository, times(1)).getAll();
    }



}



