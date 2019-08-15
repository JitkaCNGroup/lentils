package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.exception.MoreFinalPlacesException;
import dk.cngroup.lentils.repository.FinalPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class FinalPlaceService {

    private static final String FINISH_AFTER_RESUTLS_TIME_ERROR =
            "Čas vyhlášení výsledků musí být až po skončení luštění.";
    private static final String FINALPLACE_ENTITY = "finalPlace";
    private static final String RESULTSTIME_PROPERTY = "resultsTime";
    private final FinalPlaceRepository finalPlaceRepository;

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
        if (finalPlaces.isEmpty()) {
            return new FinalPlace();
        } else if (finalPlaces.size() == 1) {
            return finalPlaces.get(0);
        }

        throw new MoreFinalPlacesException("More final places found.");
    }

    public void deleteAll() {
        finalPlaceRepository.deleteAll();
    }

    public void checkFinishTimeBeforeResultsTime(final BindingResult bindingResult, final FinalPlace finalPlace) {
        if (!isFinishTimeBeforeResultsTime(finalPlace)) {
            FieldError error = new FieldError(
                    FINALPLACE_ENTITY,
                    RESULTSTIME_PROPERTY,
                    finalPlace.getFinishTime(),
                    true,
                    null,
                    null,
                    FINISH_AFTER_RESUTLS_TIME_ERROR);
            bindingResult.addError(error);
        }
    }

    public boolean isFinishTimeBeforeResultsTime(final FinalPlace finalPlace) {
        return finalPlace.getFinishTime().isBefore(finalPlace.getResultsTime());
    }
}
