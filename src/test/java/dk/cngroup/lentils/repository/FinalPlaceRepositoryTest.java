package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class FinalPlaceRepositoryTest {
    private static final Point TEST_LOCATION = new Point(59.9090442, 10.7423389);
    private static final String TEST_DESCRIPTION = "final description";
    private static final String TEST_EMPTY_DESCRIPTION = "";
    private static final String TEST_TOO_LONG_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam n";
    private static final String TEST_INVALID_FORMAT = "15:55";

    @Autowired
    FinalPlaceRepository repository;

    @Autowired
    private ObjectGenerator objectGenerator;

    @Test
    public void addTest() {
        getAnySaved();

        assertEquals(1, repository.count());
    }

    @Test
    public void findByIdTest() {
        FinalPlace finalPlaceOrig = getAnySaved();
        Optional<FinalPlace> finalPlace = repository.findById(finalPlaceOrig.getFinalPlaceId());

        assertNotNull(finalPlace);
        assertEquals(finalPlace.get(), finalPlaceOrig);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void finalPlaceWithEmptyDescriptionTest() {
        FinalPlace finalPlace = objectGenerator.generateFinalPlace();
        finalPlace.setDescription(TEST_EMPTY_DESCRIPTION);
        repository.saveAndFlush(finalPlace);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void finalPlaceWithTooLongDescriptionTest() {
        FinalPlace finalPlace = objectGenerator.generateFinalPlace();
        finalPlace.setDescription(TEST_TOO_LONG_DESCRIPTION);
        repository.saveAndFlush(finalPlace);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void finalPlaceWithEmptyLocationTest() {
        FinalPlace finalPlace = objectGenerator.generateFinalPlace();
        finalPlace.setLocation(null);
        repository.saveAndFlush(finalPlace);
    }

    @Test(expected = java.time.format.DateTimeParseException.class)
    public void finalPlaceWithFinishTimeInInvalidFormatTest() {
        LocalDateTime finishTime = LocalDateTime.parse(TEST_INVALID_FORMAT);
        FinalPlace finalPlace = objectGenerator.generateFinalPlace();
        finalPlace.setFinishTime(finishTime);
        repository.saveAndFlush(finalPlace);
    }

    @Test(expected = java.time.format.DateTimeParseException.class)
    public void finalPlaceWithResultsTimeInInvalidFormatTest() {
        LocalDateTime resultsTime = LocalDateTime.parse(TEST_INVALID_FORMAT);
        FinalPlace finalPlace = objectGenerator.generateFinalPlace();
        finalPlace.setResultsTime(resultsTime);
        repository.saveAndFlush(finalPlace);
    }

    private FinalPlace getAnySaved() {
        return repository.save(objectGenerator.generateFinalPlace());
    }
}