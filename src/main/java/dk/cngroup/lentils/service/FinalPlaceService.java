package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FinalPlaceService {

    @Autowired
    FinalPlaceRepository repository;

    public FinalPlaceService() {
    }

    public FinalPlace add(FinalPlace finalPlace) {
        return repository.save(finalPlace);
    }

    public Optional<FinalPlace> get(Long id) {
        return repository.findById(id);
    }

    /**
     * assumption:
     * there is only one line in the table
     */
    public void deleteAll() {
        repository.deleteAll();
    }
}
