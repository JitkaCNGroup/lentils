package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class FinalPlaceRepositoryTest {

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

    private FinalPlace getAnySaved() {
        return repository.save(generator.generateFinalPlace());
    }

}