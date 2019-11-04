package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.HintTakenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HintTakenServiceTest {
    @Mock
    private HintTakenRepository hintTakenRepository;

    private HintTakenService hintTakenService;
    private Team team;
    private Cypher cypher;

    @Before
    public void before() {
        hintTakenService = new HintTakenService(hintTakenRepository, hintService);
        team = new Team();
        cypher = new Cypher();
    }

    @Test
    public void testCypherHasNoHints() {
        cypher.setHints(Collections.emptyList());

        assertEquals(0, hintTakenService.getHintScore(team, cypher));
    }

    @Test
    public void testTeamDidNotTakeAnyHint() {
        cypher.setHints(getListOfHints());

        assertEquals(0, hintTakenService.getHintScore(team, cypher));
    }

    @Test
    public void testTeamTookOneHint() {
        final List<Hint> hints = getListOfHints();
        cypher.setHints(hints);
        when(hintTakenRepository.findByTeamAndHint(eq(team), eq(hints.get(1)))).thenReturn(new HintTaken());

        final int result = hintTakenService.getHintScore(team, cypher);

        assertEquals(hints.get(1).getValue(), result);
    }

    @Test
    public void testTeamTookOneHintWithRealValue() {
        final List<Hint> hints = getListOfHints();
        cypher.setHints(hints);
        when(hintTakenRepository.findByTeamAndHint(eq(team), eq(hints.get(0)))).thenReturn(new HintTaken());

        final int result = hintTakenService.getHintScore(team, cypher);

        assertEquals(5, result);
    }

    @Test
    public void testTeamTookMultipleHints() {
        final List<Hint> hints = getListOfHints();
        cypher.setHints(hints);
        when(hintTakenRepository.findByTeamAndHint(eq(team), eq(hints.get(1)))).thenReturn(new HintTaken());
        when(hintTakenRepository.findByTeamAndHint(eq(team), eq(hints.get(2)))).thenReturn(new HintTaken());

        final int result = hintTakenService.getHintScore(team, cypher);

        assertEquals(hints.get(1).getValue() + hints.get(2).getValue(), result);
    }

    @Test
    public void testTeamTookMultipleHintsWithRealValue() {
        final List<Hint> hints = getListOfHints();
        cypher.setHints(hints);
        when(hintTakenRepository.findByTeamAndHint(eq(team), eq(hints.get(1)))).thenReturn(new HintTaken());
        when(hintTakenRepository.findByTeamAndHint(eq(team), eq(hints.get(2)))).thenReturn(new HintTaken());

        final int result = hintTakenService.getHintScore(team, cypher);

        assertEquals(10, result);
    }


    private List<Hint> getListOfHints() {
        final List<Hint> list = new ArrayList<>();

        list.add(createHintWithValue(5));
        list.add(createHintWithValue(3));
        list.add(createHintWithValue(7));

        return list;
    }

    private Hint createHintWithValue(int value) {
        return new Hint("abcd", value, cypher);
    }

}
