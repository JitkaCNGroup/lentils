package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class FinalPlaceServiceIntegrationTest {
    private static final String TEST_DESCRIPTION = "final description";

    @Autowired
    private FinalPlaceService finalPlaceService;
    @Autowired
    private FinalPlaceRepository finalPlaceRepository;
    @Autowired
    private GameLogicService gameLogicService;

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

        final String placeDescription = TEST_DESCRIPTION;
        final FinalPlace newPlace = objectGenerator.generateFinalPlace();
        newPlace.setDescription(placeDescription);
        finalPlaceService.save(newPlace);

        final List<FinalPlace> placesAfterSave = finalPlaceRepository.findAll();
        assertEquals(1, placesAfterSave.size());
        assertEquals(placeDescription, placesAfterSave.get(0).getDescription());
    }

    @Test
    public void shouldBeWithinConfiguredMinutesBeforeFinishTime() {
        LocalDateTime finishTime = LocalDateTime.now().plusMinutes(20);
        final FinalPlace finalPlace = objectGenerator.generateFinalPlace();
        finalPlace.setFinishTime(finishTime);
        finalPlace.setAccessTime(30);
        finalPlaceService.save(finalPlace);

        assertTrue(gameLogicService.passedTimeToViewFinalPlace());
    }

    @Test
    public void shouldNotBeWithinConfiguredMinutesBeforeFinishTime() {
        LocalDateTime finishTime = LocalDateTime.now().plusMinutes(31);
        final FinalPlace finalPlace = objectGenerator.generateFinalPlace();
        finalPlace.setFinishTime(finishTime);
        finalPlace.setAccessTime(30);
        finalPlaceService.save(finalPlace);

        assertFalse(gameLogicService.passedTimeToViewFinalPlace());
    }

    @Test
    public void isFinishTimeBeforeResultsTime() {
        LocalDateTime finishTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime resultsTime = LocalDateTime.now().plusMinutes(20);
        int accessMinutes = 60;

        final FinalPlace finalPlace = objectGenerator.generateFinalPlace();
        finalPlace.setFinishTime(finishTime);
        finalPlace.setResultsTime(resultsTime);
        finalPlace.setAccessTime(accessMinutes);

        assertTrue(finalPlaceService.isFinishTimeBeforeResultsTime(finalPlace));
    }

    @Test
    public void isFinishTimeAfterResultsTime() {
        LocalDateTime finishTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime resultsTime = LocalDateTime.now().plusMinutes(1);
        int accessMinutes = 60;

        final FinalPlace finalPlace = objectGenerator.generateFinalPlace();
        finalPlace.setFinishTime(finishTime);
        finalPlace.setResultsTime(resultsTime);
        finalPlace.setAccessTime(accessMinutes);

        assertFalse(finalPlaceService.isFinishTimeBeforeResultsTime(finalPlace));
    }
}
