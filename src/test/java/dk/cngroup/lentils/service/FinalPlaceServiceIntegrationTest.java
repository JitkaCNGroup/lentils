package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class FinalPlaceServiceIntegrationTest {

    @Autowired
    private FinalPlaceService finalPlaceService;
    @Autowired
    private FinalPlaceRepository finalPlaceRepository;

    private ObjectGenerator objectGenerator = new ObjectGenerator();

    @Test
    public void testSaveNewFinalPlace() {
        final FinalPlace place = objectGenerator.generateFinalPlace();
        assertEquals(0, finalPlaceRepository.findAll().size());
        finalPlaceService.save(place);

        assertEquals(1, finalPlaceRepository.findAll().size());
    }

    @Test
    public void testSaveFinalPlaceOverExistingPlace() {
        final FinalPlace originalPlace = objectGenerator.generateFinalPlace();
        finalPlaceRepository.save(originalPlace);
        assertEquals(1, finalPlaceRepository.findAll().size());

        final String placeTitle = "ABCDE";
        final FinalPlace newPlace = new FinalPlace(
                placeTitle,
                new Point(8.5, 5),
                LocalDateTime.now());
        finalPlaceService.save(newPlace);

        final List<FinalPlace> placesAfterSave = finalPlaceRepository.findAll();
        assertEquals(1, placesAfterSave.size());
        assertEquals(placeTitle, placesAfterSave.get(0).getDescription());
    }
}
