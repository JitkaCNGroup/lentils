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

        assertEquals(actual.get(0).getRank().getFromPlace(), 1);
        assertEquals(actual.get(1).getRank().getFromPlace(), 2);
        assertEquals(actual.get(2).getRank().getFromPlace(), 3);
        assertEquals(actual.get(3).getRank().getFromPlace(), 4);
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

        assertEquals(actual.get(0).getRank().getFromPlace(), 1);
        assertEquals(actual.get(0).getRank().getToPlace(), 2);
        assertEquals(actual.get(1).getRank().getFromPlace(), 1);
        assertEquals(actual.get(1).getRank().getToPlace(), 2);
        assertEquals(actual.get(2).getRank().getFromPlace(), 3);
        assertEquals(actual.get(3).getRank().getFromPlace(), 4);
        assertEquals(actual.get(3).getRank().getToPlace(), 5);
        assertEquals(actual.get(4).getRank().getFromPlace(), 4);
        assertEquals(actual.get(4).getRank().getToPlace(), 5);
        assertEquals(actual.get(5).getRank().getFromPlace(), 6);
    }
}