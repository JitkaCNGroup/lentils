package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class FinalPlaceServiceTest {

    @InjectMocks
    FinalPlaceService service;

    @Mock
    FinalPlaceRepository repository;

    @Autowired
    private ObjectGenerator generator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTest() {
        FinalPlace finalPlaceOrig = getAnySaved();

        when(repository.save(finalPlaceOrig)).thenReturn(finalPlaceOrig);
        FinalPlace finalPlace = service.add(finalPlaceOrig);

        assertNotNull(finalPlace);
        assertEquals(finalPlaceOrig, finalPlace);
    }

    @Test
    public void getTest() {
        FinalPlace finalPlaceOrig = getAnySaved();

        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(finalPlaceOrig));
        Optional<FinalPlace> finalPlace = service.get(finalPlaceOrig.getId());

        assertNotNull(finalPlace);
    }

    @Test
    public void deleteTest() {
        FinalPlace finalPlaceOrig = getAnySaved();

        service.deleteAll();

        when(repository.count()).thenReturn(new Long(0));
        assertEquals(0, repository.count());
    }

    private FinalPlace getAnySaved() {
        FinalPlace finalPlaceOrig = generator.generateFinalPlace();

        when(repository.save(finalPlaceOrig)).thenReturn(finalPlaceOrig);
        return service.add(finalPlaceOrig);
    }
}