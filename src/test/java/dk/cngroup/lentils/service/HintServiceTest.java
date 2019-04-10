package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.HintRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HintServiceTest {

    @Mock
    private HintRepository hintRepository;

    @InjectMocks
    private HintService hintService;

    @Test
    public void getHintTest() {
        final Hint hint = new Hint();
        when(hintRepository.findById(any())).thenReturn(Optional.of(hint));

        final Hint returnValue = hintService.getHint(5L);

        assertEquals(hint, returnValue);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getHintTest_withInvalidId() {
        when(hintRepository.findById(any())).thenReturn(Optional.empty());

        hintService.getHint(5L);
    }

    @Test
    public void saveTest() {
        final Hint hint = new Hint();

        hintService.save(hint);

        verify(hintRepository).save(eq(hint));
    }

    @Test
    public void deleteByIdTest() {
        final Long id = 666L;
        hintService.deleteById(id);

        verify(hintRepository).deleteById(eq(id));
    }
}
