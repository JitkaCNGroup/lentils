package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinalPlaceService {

    FinalPlaceRepository finalPlacerepository;

    @Autowired
    public FinalPlaceService(FinalPlaceRepository finalPlacerepository) {
        this.finalPlacerepository = finalPlacerepository;
    }

    public FinalPlace save(FinalPlace finalPlace) {
        return finalPlacerepository.save(finalPlace);
    }

    public FinalPlace get() {
        List<FinalPlace> finalPlaces = getAll();
        if (finalPlaces.size() == 0 ){
            return new FinalPlace();
        }
        return finalPlaces.get(0);
    }

    public List<FinalPlace> getAll() {
        return finalPlacerepository.findAll();
    }

    /**
     * assumption:
     * there is only one line in the table
     */
    public void deleteAll() {
        finalPlacerepository.deleteAll();
    }
}
