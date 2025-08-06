package adeo.leroymerlin.cdp.event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void deleteById_shouldRemoveEntity_whenIdExists() {
        Event event = new Event();
        event.setTitle("PinkPop");
        eventRepository.save(event);
        assertEquals(1, eventRepository.count());
        Long eventId = event.getId();

        eventRepository.deleteById(eventId);
        assertEquals(0, eventRepository.count());
    }

    @Test
    void deleteById_shouldDoNothing_whenIdDoesNotExists() {
        assertEquals(0, eventRepository.count());
        eventRepository.deleteById(999L);
        //same database, no exception
        assertEquals(0, eventRepository.count());
    }

    @Test
    void deleteById_shouldNotThrowException_whenIdIsNull() {
        assertEquals(0, eventRepository.count());
        assertThrows(InvalidDataAccessApiUsageException.class, () -> eventRepository.deleteById(null));
    }
}