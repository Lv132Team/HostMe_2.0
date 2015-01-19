package com.softserve.edu.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.dto.EventDto;
import com.softserve.edu.model.City;
import com.softserve.edu.model.Event;
import com.softserve.edu.model.PriceCategory;
import com.softserve.edu.model.User;
import com.softserve.edu.model.routes.Place;
import com.softserve.edu.repositories.EventRepository;
import com.softserve.edu.repositories.routes.PlaceRepository;
import com.softserve.edu.service.EventService;
import com.softserve.edu.service.ProfileService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;
	@Autowired
	PlaceRepository placeRepository;

	@Autowired
	ProfileService profileService;

//	@Override
//	@Transactional
//	public void addEvent(Event event, Place place) {
//		event.setOwner(profileService.getCurrentUser());
//		eventRepository.save(event);
//	}
//
//	@Override
//	@Transactional
//	public void removeEvent(Integer id) {
//
//		eventRepository.delete(id);
//	}
//
	@Override
	@Transactional
	public List<EventDto> getAllEvents() {
		List<EventDto> list = new ArrayList<EventDto>();
		for (Event event : eventRepository.findAll()) {
			list.add(new EventDto(event, placeRepository.findOne(event.getId())));
		}
		return list;
	}
//
//	@Override
//	@Transactional
//	public Event getEvent(Integer id) {
//		return eventRepository.findOne(id);
//	}
//
//	@Override
//	@Transactional
//	public Event getEventByStartDate(Date date) {
//		return (Event) eventRepository.findByStartDate(date);
//	}
//
//	@Override
//	@Transactional
//	public List<Event> getEventByCity(City city) {
//		return eventRepository.findByCity(city);
//	}
//
//	@Override
//	@Transactional
//	public List<Event> getEventByOwner(User owner) {
//		return eventRepository.findByOwner(owner);
//	}
//
//	@Override
//	@Transactional
//	public List<Event> getEventByPriceCategory(PriceCategory priceCategory) {
//		return eventRepository.findByPriceCategory(priceCategory);
//	}
//
//	@Override
//	@Transactional
//	public List<Event> getEventByWebSite(String website) {
//		return eventRepository.findByWebsite(website);
//	}
//
//	@Override
//	@Transactional
//	public void saveEvent(Event event) {
//		eventRepository.save(event);
//	}

}
