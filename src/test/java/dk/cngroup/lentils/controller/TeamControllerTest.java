package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.TeamService;
import dk.cngroup.lentils.utils.AssertionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TeamControllerTest {
    @InjectMocks
    private TeamController testedController;

    @Mock
    private TeamService teamService;

    private BindingResult bindingResultMock;
    private Model modelMock;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        bindingResultMock = mock(BindingResult.class);
        modelMock = mock(Model.class);
    }

    @Test
    public void testAddTeam_shouldNotRedirectOnError() {
        when(bindingResultMock.hasErrors()).thenReturn(true);

        final String result = testedController.addTeam(new Team(), bindingResultMock, modelMock);

        AssertionUtils.assertValueIsNotRedirection(result);
    }

    @Test
    public void testAddTeam_shouldRedirectOnSuccess() {
        when(bindingResultMock.hasErrors()).thenReturn(false);

        final String result = testedController.addTeam(new Team(), bindingResultMock, modelMock);

        AssertionUtils.assertValueIsRedirection(result);
    }


    @Test
    public void testUpdateTeam_shouldNotRedirectOnError() {
        when(bindingResultMock.hasErrors()).thenReturn(true);

        final String result = testedController.update(1L, new Team(), bindingResultMock, modelMock);

        AssertionUtils.assertValueIsNotRedirection(result);
    }

    @Test
    public void testUpdateTeam_shouldRedirectOnSuccess() {
        when(bindingResultMock.hasErrors()).thenReturn(false);

        final String result = testedController.update(1L, new Team(), bindingResultMock, modelMock);

        AssertionUtils.assertValueIsRedirection(result);
    }

    @Test
    public void testDelete_shouldRedirect() {
        final String result = testedController.delete(1L);

        AssertionUtils.assertValueIsRedirection(result);
    }
}