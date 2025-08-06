package adeo.leroymerlin.cdp.event;

import adeo.leroymerlin.cdp.band.BandDto;
import adeo.leroymerlin.cdp.member.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import util.EventTestFactory;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    String query = "hellfest";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void findEvents_shouldReturnListOfEvents() throws Exception {
        List<Event> events = List.of(EventTestFactory.createFullEvent());
        when(eventService.getEvents()).thenReturn(events);

        mockMvc.perform(get("/api/events/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Hellfest"));

        verify(eventService).getEvents();
    }

    @Test
    void findEventByQuery_shouldReturnEvent() throws Exception {
        EventWithCountViewDto event = EventTestFactory.createFullEventWithCount();
        when(eventService.getFilteredEvents(query)).thenReturn(List.of(event));

        mockMvc.perform(get("/api/events/search/" + query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Hellfest [2]"))
                .andExpect(jsonPath("$[0].imgUrl").value("https://example.com/image.jpg"))
                .andExpect(jsonPath("$[0].bands[*].name", containsInAnyOrder("Band A [2]", "Band B [2]")))
                .andExpect(jsonPath("$[0].nbStars").value(5))
                .andExpect(jsonPath("$[0].comment").value("Best festival ever!"));
    }

    @Test
    void deleteEvent_shouldCallService() throws Exception {
        Long eventId = 1L;

        mockMvc.perform(delete("/api/events/" + eventId))
                .andExpect(status().isOk());

        verify(eventService).delete(eventId);
    }

    @Test
    void updateEvent_shouldCallService() throws Exception {
        Long eventId = 1L;
        EventDto event = new EventDto("Updated Event", "https://example.com/updated-image.jpg",
                Set.of(new BandDto("Updated Band A", Set.of(new MemberDto("Member A"))), new BandDto("Updated Band B", Set.of(new MemberDto("Member B"))))
                , 4, "Updated comment");
        String jsonBody = """
                {
                    "title": "Updated Event",
                    "imgUrl": "https://example.com/updated-image.jpg",
                    "bands": [
                        {"name": "Updated Band A", "members": [{"name": "Member A"}]},
                        {"name": "Updated Band B", "members": [{"name": "Member B"}]}
                    ],
                    "nbStars": 4,
                    "comment": "Updated comment"
                }
                """;

        doNothing().when(eventService).update(eventId, event);

        mockMvc.perform(put("/api/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());

        verify(eventService).update(eventId, event);
    }
}