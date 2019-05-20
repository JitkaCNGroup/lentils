package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.core.ScoreItem;
import dk.cngroup.lentils.entity.view.Rank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankService {

    public <T extends ScoreItem> List<T> computeRank(final List<T> items) {
        List<ScoreItem> sameScoreItems = new ArrayList();
        int fromPlace = 1;
        for (int i = 0; i < items.size(); i++) {
            if (sameScoreItems.isEmpty()) {
                fromPlace = getPlaceFromIndex(i);
            }

            sameScoreItems.add(items.get(i));

            if (isLastIndex(items, i) || !hasNextItemSameScore(items, i)) {
                int toPlace = getPlaceFromIndex(i);
                setRanking(sameScoreItems, fromPlace, toPlace);
                sameScoreItems.clear();
            }
        }
        return items;
    }

    private int getPlaceFromIndex(final int index) {
        return index + 1;
    }

    private <T extends ScoreItem> boolean hasNextItemSameScore(final List<T> items, final int index) {
        return items.get(index).getScore() == items.get(index + 1).getScore();
    }

    private boolean isLastIndex(final List<?> items, final int index) {
        return index == items.size() - 1;
    }

    private void setRanking(final List<ScoreItem> sameScoreItems, final int fromPlace, final int toPlace) {
        Rank rank = new Rank(fromPlace, toPlace);
        sameScoreItems.forEach(e -> e.setRank(rank));
    }
}
