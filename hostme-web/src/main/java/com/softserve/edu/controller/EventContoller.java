package com.softserve.edu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softserve.edu.dto.EventDto;
import com.softserve.edu.model.Event;
import com.softserve.edu.model.Group;
import com.softserve.edu.model.Sightseeing;
import com.softserve.edu.model.User;
import com.softserve.edu.service.EventService;
import com.softserve.edu.service.ProfileService;

@Controller
public class EventContoller {

	@Autowired
	EventService eventService;
	@Autowired
	ProfileService profileService;

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String showEvents(Model model) {
		return "events";
	}

	@RequestMapping(value = "/all-events", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<EventDto> getAllEvents() {
		List<EventDto> events = eventService.getAllEvents();
		return events;
	}

	@RequestMapping(value = "/my-events", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<EventDto> getMyEvents() {
		User user = profileService.getUserByLogin(SecurityContextHolder
				.getContext().getAuthentication().getName());
		List<EventDto> events = eventService.getEventByOwner(user);
		return events;
	}

	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public String showEvent(@RequestParam("id") Integer id, Model model) {
		EventDto eventDto = eventService.getEvent(id);
		model.addAttribute("event", eventDto);
		return "event";
	}

	@RequestMapping(value = "/event", method = RequestMethod.POST)
	public String editEvent(@ModelAttribute("event") final EventDto eventDto) {
		Event event = eventService.convertEventDtoToEvent(eventDto);
		eventService.saveEvent(event);
		return "event";
	}

	@RequestMapping(value = "/event-creation", method = RequestMethod.GET)
	public String addEvent(Model model) {
		Event event = new Event();
		model.addAttribute("event", event);
		return "event-creation";
	}

}
