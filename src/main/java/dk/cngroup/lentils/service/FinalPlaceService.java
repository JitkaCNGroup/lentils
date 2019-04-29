package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.exception.MoreFinalPlacesException;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FinalPlaceService {

    private FinalPlaceRepository finalPlaceRepository;

    @Autowired
    public FinalPlaceService(final FinalPlaceRepository finalPlaceRepository) {
        this.finalPlaceRepository = finalPlaceRepository;
    }

    public FinalPlace save(final FinalPlace finalPlace) {
        final FinalPlace existingPlace = getFinalPlace();
        finalPlace.setFinalPlaceId(existingPlace.getFinalPlaceId());

        return finalPlaceRepository.save(finalPlace);
    }

    public FinalPlace getFinalPlace() {
        List<FinalPlace> finalPlaces = finalPlaceRepository.findAll();
        if (finalPlaces.size() == 0) {
            return new FinalPlace();
        } else if (finalPlaces.size() == 1) {
            return finalPlaces.get(0);
        }

        throw new MoreFinalPlacesException("More final places found.");
    }

    public void deleteAll() {
        finalPlaceRepository.deleteAll();
    }
}
