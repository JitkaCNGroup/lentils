package dk.cngroup.lentils.service;

import dk.cngroup.lentils.dto.ContactLinkDTO;
import dk.cngroup.lentils.entity.ContactLink;
import dk.cngroup.lentils.repository.ContactLinkRepository;
import dk.cngroup.lentils.service.convertors.ModelMapperWrapper;
import dk.cngroup.lentils.service.convertors.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactLinkService {

    private final ContactLinkRepository contactLinkRepository;
    private final ObjectMapper mapper;

    @Autowired
    public ContactLinkService(final ContactLinkRepository contactLinkRepository,
                              final ModelMapperWrapper modelMapperWrapper) {
        this.contactLinkRepository = contactLinkRepository;
        this.mapper = modelMapperWrapper;
    }

    public List<ContactLink> getLinks() {
        return contactLinkRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    public void updateLinks(final List<ContactLinkDTO> updateLinks) {
        final List<ContactLinkDTO> links = filterDtosFromFrontend(updateLinks);
        final List<ContactLink> entities = contactLinkRepository.findAll();
        final List<Long> entityIds = entities.stream().map(ContactLink::getId).collect(Collectors.toList());
        final List<Long> dtoIds = links.stream().map(ContactLinkDTO::getId).collect(Collectors.toList());

        updateExisting(links, entityIds);
        deleteRemoved(dtoIds, entities);
        addNew(links);
    }

    /**
     * When you delete the link via a button and submit the form, Spring binds the list in a way that
     * missing indices in the list are just new ContactLink objects with null values.
     * We need to get rid of them manually here.
     *
     * @param links Link DTOs sent from FE
     *
     * @return Filtered entity DTOs that can be updated.
     */
    private List<ContactLinkDTO> filterDtosFromFrontend(final List<ContactLinkDTO> links) {
        return links.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toList());
    }

    private void updateExisting(final List<ContactLinkDTO> links, final List<Long> entityIds) {
        final List<ContactLinkDTO> updateLinks = links.stream()
                .filter(link -> entityIds.contains(link.getId()))
                .collect(Collectors.toList());

        updateLinks.forEach(dto -> {
            final Optional<ContactLink> fetchedEntity = contactLinkRepository.findById(dto.getId());
            if (!fetchedEntity.isPresent()) {
                return;
            }

            final ContactLink entity = fetchedEntity.get();
            mapper.map(dto, entity);

            contactLinkRepository.save(entity);
        });
    }

    private void deleteRemoved(final List<Long> linkIds, final List<ContactLink> entities) {
        final List<ContactLink> removedEntities = entities.stream()
                .filter(entity -> !linkIds.contains(entity.getId()))
                .collect(Collectors.toList());

        removedEntities.forEach(contactLinkRepository::delete);
    }

    private void addNew(final List<ContactLinkDTO> links) {
        final List<ContactLinkDTO> newLinks = links.stream()
                .filter(e -> e.getId() == -1L)
                .collect(Collectors.toList());

        newLinks.forEach(dto -> {
            final ContactLink entity = new ContactLink();
            mapper.map(dto, entity);

            contactLinkRepository.save(entity);
        });
    }
}
