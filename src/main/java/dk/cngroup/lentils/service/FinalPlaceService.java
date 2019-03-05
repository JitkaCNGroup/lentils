package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.exception.MoreFinalPlacesException;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * assumption:
     * there is only one line in the table
     */
    public FinalPlace getFinalPlace() {
        List<FinalPlace> finalPlaces = getAll();
        if (finalPlaces.size() == 0) {
            return new FinalPlace();
        } else if (finalPlaces.size() == 1) {
            return finalPlaces.get(0);
        } else {
            throw new MoreFinalPlacesException("More final places found.");
        }
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
