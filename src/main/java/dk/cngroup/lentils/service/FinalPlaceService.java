package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FinalPlaceService {

    FinalPlaceRepository finalPlacerepository;

    @Autowired
    public FinalPlaceService(FinalPlaceRepository finalPlacerepository) {
        this.finalPlacerepository = finalPlacerepository;
    }

    public FinalPlace add(FinalPlace finalPlace) {
        return finalPlacerepository.save(finalPlace);
    }

    public Optional<FinalPlace> get(Long id) {
        return finalPlacerepository.findById(id);
    }

    /**
     * assumption:
     * there is only one line in the table
     */
    public void deleteAll() {
        finalPlacerepository.deleteAll();
    }
}
