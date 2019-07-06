package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.core.ScoreItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LentilsApplication.class)
public class RankServiceTest {

    @Autowired
    private RankService rankService;

    @Test
    public void computeRankOnEmptyList() {
        List<ScoreItem> scoreItems = Collections.emptyList();

        List<ScoreItem> actual = rankService.computeRank(scoreItems);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void computeRankOnList() {
        List<ScoreItem> scoreItems = new ArrayList<>();
        scoreItems.add(new ScoreItem(20));
        scoreItems.add(new ScoreItem(12));
        scoreItems.add(new ScoreItem(7));
        scoreItems.add(new ScoreItem(5));

        List<ScoreItem> actual = rankService.computeRank(scoreItems);

        assertEquals(1, actual.get(0).getRank().getFromPlace());
        assertEquals(2, actual.get(1).getRank().getFromPlace());
        assertEquals(3, actual.get(2).getRank().getFromPlace());
        assertEquals(4, actual.get(3).getRank().getFromPlace());
    }

    @Test
    public void computeRankOnListWithSameScoreItems() {
        List<ScoreItem> scoreItems = new ArrayList<>();
        scoreItems.add(new ScoreItem(20));
        scoreItems.add(new ScoreItem(20));
        scoreItems.add(new ScoreItem(7));
        scoreItems.add(new ScoreItem(5));
        scoreItems.add(new ScoreItem(5));
        scoreItems.add(new ScoreItem(4));

        List<ScoreItem> actual = rankService.computeRank(scoreItems);

        assertEquals(1, actual.get(0).getRank().getFromPlace());
        assertEquals(2, actual.get(0).getRank().getToPlace());
        assertEquals(1, actual.get(1).getRank().getFromPlace());
        assertEquals(2, actual.get(1).getRank().getToPlace());
        assertEquals(3, actual.get(2).getRank().getFromPlace());
        assertEquals(4, actual.get(3).getRank().getFromPlace());
        assertEquals(5, actual.get(3).getRank().getToPlace());
        assertEquals(4, actual.get(4).getRank().getFromPlace());
        assertEquals(5, actual.get(4).getRank().getToPlace());
        assertEquals(6, actual.get(5).getRank().getFromPlace());
    }
}