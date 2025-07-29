package com.ecdms.ecdms.controller;

import com.ecdms.ecdms.dto.common.StandardResponse;
import com.ecdms.ecdms.dto.request.CalendarDTO;
import com.ecdms.ecdms.dto.request.EventsDTO;
import com.ecdms.ecdms.dto.response.EventColorDTO;
import com.ecdms.ecdms.dto.response.EventDTO;
import com.ecdms.ecdms.entity.Event;
import com.ecdms.ecdms.entity.EventColor;
import com.ecdms.ecdms.repository.EventColorRepository;
import com.ecdms.ecdms.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@CrossOrigin("*")
@Slf4j
public class EventController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventColorRepository eventColorRepository;

    @GetMapping
    public List<EventDTO> getAllEvents() {
        List<Event> all = eventRepository.findAll();
        List<EventDTO> eventDTOList = new ArrayList<>();
        for(Event event:all){
            Optional<EventColor> byId = eventColorRepository.findById(event.getColor_id());
            EventColorDTO eventColorDTO = new EventColorDTO(byId.get().getPrimary(),byId.get().getSecondary());
            EventDTO eventDTO = new EventDTO(
                    event.getId(),
                    event.getTitle(),
                    event.getStart(),
                    event.getEnd(),
                    eventColorDTO
            );
            eventDTOList.add(eventDTO);
        }
        return eventDTOList;
    }

    @PostMapping("/save")
    public ResponseEntity createEvent(@RequestBody CalendarDTO eventDTOs) {
        eventRepository.deleteAll();
        for (EventsDTO eventDTO:eventDTOs.getEvents()){
            EventColor eventColor = new EventColor(eventDTO.getColor().getPrimary(),eventDTO.getColor().getSecondary());
            eventColorRepository.save(eventColor);

            Event event = new Event(
                    eventDTO.getTitle(),
                    eventDTO.getStart(),
                    eventDTO.getEnd(),
                    eventColor.getEventColorID()
            );
            eventRepository.save(event);
        }
        return new ResponseEntity(new StandardResponse(true,"Event added"), HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    public Event updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
//        return eventRepository.findById(updatedEvent.getId()).map(event -> {
//            event.setTitle(updatedEvent.getTitle());
//            event.setStart(updatedEvent.getStart());
//            event.setEnd(updatedEvent.getEnd());
//            event.setColor(updatedEvent.getColor());
//            return eventRepository.save(event);
//        }).orElseThrow(() -> new RuntimeException("Event not found"));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable Integer id) {
        Optional<Event> byId = eventRepository.findById(id);
        eventColorRepository.deleteById(byId.get().getColor_id());
        eventRepository.deleteById(id);

        return new ResponseEntity(new StandardResponse(true,"Deleted Successfully."),HttpStatus.OK);
    }
}
