package dk.cngroup.lentils.service;

import dk.cngroup.lentils.dto.ContactLinkDTO;
import dk.cngroup.lentils.entity.ContactLink;
import dk.cngroup.lentils.repository.ContactLinkRepository;
import dk.cngroup.lentils.service.convertors.ModelMapperWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContactLinkServiceTest {
    @Mock
    private ContactLinkRepository contactLinkRepository;

    private ContactLinkService contactLinkService;

    @Before
    public void before() {
        contactLinkService = new ContactLinkService(contactLinkRepository, new ModelMapperWrapper());
    }

    @Test
    public void testThatNullObjectsAreIgnored() {
        final List<ContactLink> existingEntities = createListOfEntities();
        final List<ContactLinkDTO> updateList = createDtosFromEntities(existingEntities);
        updateList.add(new ContactLinkDTO());

        when(contactLinkRepository.findAll()).thenReturn(existingEntities);

        contactLinkService.updateLinks(updateList);

        verify(contactLinkRepository, times(0)).delete(any());
        verify(contactLinkRepository, times(0)).save(any());
        verify(contactLinkRepository, times(0)).saveAll(any());
    }

    @Test
    public void testThatNewDtoIsSaved() {
        final List<ContactLink> existingEntities = createListOfEntities();
        final List<ContactLinkDTO> updateList = createDtosFromEntities(existingEntities);
        final ContactLinkDTO newDto = new ContactLinkDTO();
        newDto.setId(-1L);
        updateList.add(newDto);

        setupRepositoryMock(existingEntities);

        contactLinkService.updateLinks(updateList);

        verify(contactLinkRepository, times(0)).delete(any());
        verify(contactLinkRepository, times(updateList.size())).save(any());
    }

    @Test
    public void testThatRemovedEntityIsDeleted() {
        final List<ContactLink> existingEntities = createListOfEntities();
        final List<ContactLinkDTO> updateList = createDtosFromEntities(existingEntities);
        updateList.remove(0);

        setupRepositoryMock(existingEntities);

        contactLinkService.updateLinks(updateList);

        verify(contactLinkRepository, times(1)).delete(any());
    }

    @Test
    public void testThatEntityIsUpdated() {
        final List<ContactLink> existingEntities = createListOfEntities();
        final List<ContactLinkDTO> updateList = createDtosFromEntities(existingEntities);
        updateList.get(0).setTitle("updatedTitle");
        updateList.get(0).setUrl("updatedUrl");

        setupRepositoryMock(existingEntities);

        contactLinkService.updateLinks(updateList);

        verify(contactLinkRepository, times(0)).delete(any());
        verify(contactLinkRepository, times(updateList.size())).save(any());
        verify(contactLinkRepository, times(0)).saveAll(any());
    }

    private List<ContactLink> createListOfEntities() {
        final List<ContactLink> list = new ArrayList<>(3);

        list.add(createLink(1, "A"));
        list.add(createLink(2, "B"));
        list.add(createLink(3, "C"));

        return list;
    }

    private List<ContactLinkDTO> createDtosFromEntities(final List<ContactLink> entities) {
        final ModelMapperWrapper mapper = new ModelMapperWrapper();

        return entities.stream()
                .map(e -> {
                    final ContactLinkDTO dto = new ContactLinkDTO();
                    mapper.map(e, dto);
                    return dto;
                }).collect(Collectors.toList());
    }

    private ContactLink createLink(final long id, final String title) {
        final ContactLink link = new ContactLink();
        link.setId(id);
        link.setTitle(title);
        link.setUrl(title + ".cz");

        return link;
    }

    private void setupRepositoryMock(final List<ContactLink> existingEntities) {
        when(contactLinkRepository.findAll()).thenReturn(existingEntities);

        existingEntities.forEach(e -> when(contactLinkRepository.findById(eq(e.getId())))
                .thenReturn(java.util.Optional.ofNullable(e)));
    }
}
