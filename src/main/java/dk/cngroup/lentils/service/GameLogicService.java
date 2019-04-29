package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.FinalPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GameLogicService {

    private final FinalPlaceService finalPlaceService;

    @Autowired
    public GameLogicService(final FinalPlaceService finalPlaceService) {
        this.finalPlaceService = finalPlaceService;
    }

    public boolean isGameInProgress() {
        final FinalPlace finalPlace = finalPlaceService.getFinalPlace();

        return finalPlace.getOpeningTime() != null && finalPlace.getOpeningTime().isAfter(LocalDateTime.now());
    }

    public boolean allowPlayersToViewFinalPlace() {
        LocalDateTime finalPlaceOpeningTime = finalPlaceService.getFinalPlace().getOpeningTime();
        return finalPlaceOpeningTime.isBefore(LocalDateTime.now().plusHours(1));
    }
}
