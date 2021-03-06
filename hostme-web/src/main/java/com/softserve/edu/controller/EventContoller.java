package com.softserve.edu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softserve.edu.dto.EventDto;
import com.softserve.edu.model.City;
import com.softserve.edu.model.Country;
import com.softserve.edu.model.Event;
import com.softserve.edu.model.PriceCategory;
import com.softserve.edu.model.User;
import com.softserve.edu.repositories.EventRepository;
import com.softserve.edu.service.CityService;
import com.softserve.edu.service.CountryService;
import com.softserve.edu.service.EventService;
import com.softserve.edu.service.ImageService;
import com.softserve.edu.service.PriceCategoryService;
import com.softserve.edu.service.ProfileService;

@Controller
public class EventContoller {

	@Autowired
	EventService eventService;
	@Autowired
	ProfileService profileService;
	@Autowired
	CountryService countryService;
	@Autowired
	CityService cityService;
	@Autowired
	PriceCategoryService priceCategoryService;
	@Autowired
	EventRepository eventRepository;
	@Autowired
	ImageService imageService;

	public static final String REDIRECT_EVENT_ID_VALUE = "redirect:/event?id={id}";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String REDIRECT_EVENTS = "redirect:/events";

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String showEvents(Model model) {
		List<EventDto> events = eventService.getAllEvents();
		Event event = new Event();
		List<Country> countries = countryService.getAllCountry();
		List<City> cities = cityService.getAllCity();
		List<PriceCategory> priceCategories = priceCategoryService
				.getAllPriceCategory();
		model.addAttribute("events", events);
		model.addAttribute("event", event);
		model.addAttribute("countries", countries);
		model.addAttribute("cities", cities);
		model.addAttribute("priceCategories", priceCategories);
		return "events";
	}

	@RequestMapping(value = "/events", method = RequestMethod.POST)
	public String addEvent(Model model, @ModelAttribute("event") Event event) {
		User user = profileService.getUserByLogin(SecurityContextHolder
				.getContext().getAuthentication().getName());
		String priceCategory = event.getPriceCategory().getPriceCategory();
		String city = event.getCity().getCity();
		event.setOwner(user);
		eventService.addEvent(event, priceCategory, city);
		return REDIRECT_EVENTS;
	}
	/**
	 * Returns JSON with all events for moderator with fixed according to pagination size
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value = "/all-events", params = { "page", "size",
			"orderBy", "orderType" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EventDto> getAllEventsPaging(
			@RequestParam(value = "page") Integer page,
			@RequestParam(value = "size") Integer size,
			@RequestParam(value = "orderBy") String orderBy,
			@RequestParam(value = "orderType") String orderType) {
		return eventService.getAllEventsPaging(page, size, orderBy, orderType);
	}
	/**
	 * Method for obtaining number of pages with fixed size of page
	 * @param size
	 * @param sender
	 * @return
	 */
	@RequestMapping(value = "/paging", params = { "size", "sender" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Long getEventsPaging(
			@RequestParam(value = "size") Long size,
			@RequestParam(value = "sender") String sender) {
		return eventService.getPageCount(size, sender);
	}
	/**
	 * Returns events created by user
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value = "/my-events", params = { "page", "size", "orderBy",
			"orderType" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EventDto> getMyEvents(
			@RequestParam(value = "page") Integer page,
			@RequestParam(value = "size") Integer size,
			@RequestParam(value = "orderBy") String orderBy,
			@RequestParam(value = "orderType") String orderType) {
		return eventService.getEventByOwner(page, size, orderBy, orderType);
	}
	/**
	 * Returns events that user are signed on
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value = "/signed-events", params = { "page", "size",
			"orderBy", "orderType" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EventDto> getSignedEvents(
			@RequestParam(value = "page") Integer page,
			@RequestParam(value = "size") Integer size,
			@RequestParam(value = "orderBy") String orderBy,
			@RequestParam(value = "orderType") String orderType) {
		return eventService.getByAttendee(page, size, orderBy, orderType);
	}
	/**
	 * Single event page
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public String showEvent(@RequestParam("id") Integer id, Model model) {
		EventDto eventDto = eventService.getEvent(id);
		User user = profileService.getCurrentUser();
		List<Country> countries = countryService.getAllCountry();
		List<City> cities = cityService.getAllCity();
		List<PriceCategory> priceCategories = priceCategoryService
				.getAllPriceCategory();
		boolean isCreator = eventService.checkEventOwner(eventDto, user);
		boolean isSubscribed = eventService
				.checkEventSubscribed(eventDto, user);
		model.addAttribute("event", eventDto);
		model.addAttribute("countries", countries);
		model.addAttribute("cities", cities);
		model.addAttribute("priceCategories", priceCategories);
		model.addAttribute("isCreator", isCreator);
		model.addAttribute("isSubscribed", isSubscribed);
		return "event";
	}

	@RequestMapping(value = "/event", method = RequestMethod.POST)
	public String editEvent(@ModelAttribute("event") final Event event,
			RedirectAttributes redirectAttributes) {
		String priceCategory = event.getPriceCategory().getPriceCategory();
		String city = event.getCity().getCity();
		event.setOwner(profileService.getUserByLogin(SecurityContextHolder
				.getContext().getAuthentication().getName()));
		eventService.updateEvent(event, city, priceCategory);
		redirectAttributes.addAttribute("id", event.getId()).addFlashAttribute(
				"eventEdited", true);
		return REDIRECT_EVENT_ID_VALUE;
	}
	/**
	 * Removes event and redirects on single event page
	 * @param id
	 * @return
	 */
	@RequestMapping("/event/delete/{id}")
	public String deleteEvent(@PathVariable("id") Integer id) {
		Event event = eventService.findOne(id);
		eventService.removeEvent(event);
		return REDIRECT_EVENTS;
	}
	/**
	 * Updates status of event
	 * @param event
	 * @return
	 */
	@RequestMapping(value = "/event-update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Event updateEventStatus(@RequestBody Event event) {
		Event newEvent = eventService.findOne(event.getId());
		newEvent.setStatus(event.getStatus());
		eventService.saveEvent(newEvent);
		return event;
	}

	@RequestMapping(value = "/addPhotosToEvent", method = RequestMethod.POST)
	public String addPhotoToEvent(@RequestParam("file") MultipartFile[] files,
			@ModelAttribute("sightseeing") final Event event,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("id", event.getId()).addFlashAttribute(
				"eventEdited", true);
		imageService.addImagesToEvent(files, event);
		return REDIRECT_EVENT_ID_VALUE;
	}
	/**
	 * Used for parsing string date and changed to format class Date
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		String datePattern = DATE_FORMAT;
		SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	/**
	 * Used for joining or leaving according to existing subscribtion state
	 * @param id
	 * @return
	 */
	@RequestMapping("/event-subscribe/{id}")
	public String eventJoin(@PathVariable("id") Integer id) {
		User user = profileService.getCurrentUser();
		EventDto eventDto = eventService.getEvent(id);
		if (eventService.checkEventSubscribed(eventDto, user)) {
			eventService.leaveEvent(user, id);
		} else {
			eventService.addAttendee(user, id);
		}
		return REDIRECT_EVENT_ID_VALUE;
	}

}
