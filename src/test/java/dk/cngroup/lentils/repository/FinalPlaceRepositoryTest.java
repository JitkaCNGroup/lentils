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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class FinalPlaceRepositoryTest {
    private static final Point TEST_LOCATION = new Point(59.9090442, 10.7423389);
    private static final String TEST_TITLE = "final title";
    private static final String TEST_EMPTY_TITLE = "";
    private static final String TEST_256_TITLE = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Donec vitae arcu. Duis sapien nunc, commodo et, interdum suscipit, sollicitudin et, dolor. Lorem ipsum dolor sit amet, consectetuer!";

    @Autowired
    FinalPlaceRepository repository;

    @Autowired
    private ObjectGenerator generator;

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
    public void finalPlaceWithEmptyTitleTest() {
        FinalPlace finalPlace = new FinalPlace(TEST_EMPTY_TITLE, TEST_LOCATION, null);
        repository.saveAndFlush(finalPlace);
    }

    @Test
    public void finalPlaceWith256TitleTest() {
        FinalPlace finalPlace = new FinalPlace(TEST_256_TITLE, TEST_LOCATION, null);
        repository.saveAndFlush(finalPlace);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void finalPlaceWithEmptyLocationTest() {
        FinalPlace finalPlace = new FinalPlace(TEST_TITLE, null, null);
        repository.saveAndFlush(finalPlace);
    }

    private FinalPlace getAnySaved() {
        return repository.save(generator.generateFinalPlace());
    }

}