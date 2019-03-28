package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.exception.MoreFinalPlacesException;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FinalPlaceServiceTest {

    @InjectMocks
    FinalPlaceService service;

    @Mock
    FinalPlaceRepository repository;

    private ObjectGenerator generator = new ObjectGenerator();

    @Test
    public void addTest() {
        FinalPlace finalPlaceOrig = getAnySaved();

        when(repository.save(finalPlaceOrig)).thenReturn(finalPlaceOrig);
        FinalPlace finalPlace = service.save(finalPlaceOrig);

        assertNotNull(finalPlace);
        assertEquals(finalPlaceOrig, finalPlace);
    }

    @Test
    public void deleteTest() {
        FinalPlace finalPlaceOrig = getAnySaved();

        service.deleteAll();

        when(repository.count()).thenReturn(0L);
        assertEquals(0, repository.count());
    }

    @Test
    public void testGetFinalPlaceWithNoPlaceSet() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        FinalPlace result = service.getFinalPlace();

        assertNotNull(result);
    }

    @Test
    public void testGetFinalPlaceWithOnePlaceSet() {
        final FinalPlace expectedPlace = new FinalPlace();
        final List<FinalPlace> list = new ArrayList<>();
        list.add(expectedPlace);

        when(repository.findAll()).thenReturn(list);

        FinalPlace result = service.getFinalPlace();

        assertEquals(expectedPlace, result);
    }

    @Test(expected = MoreFinalPlacesException.class)
    public void testGetFinalPlaceWithMultiplePlacesSet() {
        final List<FinalPlace> list = new ArrayList<>();
        list.add(new FinalPlace());
        list.add(new FinalPlace());

        when(repository.findAll()).thenReturn(list);

        service.getFinalPlace();
    }

    private FinalPlace getAnySaved() {
        FinalPlace finalPlaceOrig = generator.generateFinalPlace();

        when(repository.save(finalPlaceOrig)).thenReturn(finalPlaceOrig);
        return service.save(finalPlaceOrig);
    }
}
