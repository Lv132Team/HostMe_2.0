package com.softserve.edu.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.dto.SightseeingDto;
import com.softserve.edu.model.Sightseeing;
import com.softserve.edu.model.Status;
import com.softserve.edu.model.User;
import com.softserve.edu.repositories.CityRepository;
import com.softserve.edu.repositories.CountryRepository;
import com.softserve.edu.repositories.ImageRepository;
import com.softserve.edu.repositories.PriceCategoryRepository;
import com.softserve.edu.repositories.SightseeingRepository;
import com.softserve.edu.repositories.SystemPropertiesRepository;
import com.softserve.edu.repositories.routes.PlaceRepository;
import com.softserve.edu.repositories.routes.RouteRepository;
import com.softserve.edu.repositories.user.UserRepository;
import com.softserve.edu.service.ProfileService;
import com.softserve.edu.service.SightseeingService;
import com.softserve.edu.service.SystemPropertiesService;

@Service
@Transactional
public class SightseeingServiceImpl implements SightseeingService {

	@Autowired
	private SightseeingRepository sightseeingRepository;
	@Autowired
	PlaceRepository placeRepository;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private PriceCategoryRepository priceCategoryRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	SystemPropertiesService systemPropertiesService;
	@Autowired
	RouteRepository routeRepository;

	@Autowired
	private SystemPropertiesRepository systemPropertiesRepository;

	public final Integer PROPERTY_ID = 1;

	public boolean haveSight(int id) {
		return sightseeingRepository.exists(id);
	}

	@Override
	public List<Sightseeing> searchSightseeing(
			Specifications<Sightseeing> specifications) {
		return sightseeingRepository.findAll(specifications);
	}

	/**
	 * method all sightseeings for moderator
	 */
	@Override
	public List<SightseeingDto> getAllSightseeings(Integer page, Integer size,
			String orderBy, String orderType) {
		List<SightseeingDto> list = new ArrayList<SightseeingDto>();
		for (Sightseeing sightseeing : sightseeingRepository
				.findAll(getPageRequest(page, size, orderBy, orderType))) {
			list.add(new SightseeingDto(sightseeing, placeRepository
					.findOne(sightseeing.getId())));
		}
		return list;
	}

	/**
	 * method favourite sightseeings which user liked
	 */
	public List<SightseeingDto> getFavouriteSightseeings(User liker,
			Integer page, Integer size, String orderBy, String orderType) {
		List<SightseeingDto> list = new ArrayList<SightseeingDto>();

		for (Sightseeing sightseeing : sightseeingRepository.findByLikers(
				liker, getPageRequest(page, size, orderBy, orderType))) {
			list.add(new SightseeingDto(sightseeing, placeRepository
					.findOne(sightseeing.getId())));
		}
		return list;
	}

	/**
	 * method sightseeings that were created by the user
	 */
	@Override
	public List<SightseeingDto> getSightseeingByOwner(Integer page,
			Integer size, String orderBy, String orderType) {
		List<SightseeingDto> list = new ArrayList<SightseeingDto>();
		User owner = profileService.getCurrentUser();
		for (Sightseeing sightseeing : sightseeingRepository.findByOwner(owner,
				getPageRequest(page, size, orderBy, orderType))) {
			list.add(new SightseeingDto(sightseeing, placeRepository
					.findOne(sightseeing.getId())));
		}
		return list;
	}

	@Override
	public List<SightseeingDto> getSightseeingsDtoList(
			List<Sightseeing> sightseeings) {
		List<SightseeingDto> result = new ArrayList<>();
		String propertiesImageUrl = systemPropertiesService.getImageUrl();
		for (Sightseeing sightseeing : sightseeings) {
			result.add(new SightseeingDto(sightseeing, propertiesImageUrl));
		}
		return result;
	}

	@Override
	public Sightseeing findOne(Integer id) {
		return sightseeingRepository.findOne(id);
	}

	/**
	 * delete sightseeing, route that related with this sightseeing and from
	 * other interim tables
	 */
	@Override
	@PreAuthorize("#sightseeing.owner.login == authentication.name or hasRole('MODERATOR')")
	public void deleteSightseeing(Sightseeing sightseeing) {
		Integer[] test = placeRepository.getIdDeltedRoutes(sightseeing.getId());
		placeRepository.deletePlaceRoute(sightseeing.getId());
		sightseeingRepository.deleteLikefromSightseeing(sightseeing.getId());
		for (int i = 0; i < test.length; i++) {
			routeRepository.delete(test[i]);
		}
		sightseeingRepository.delete(sightseeing);
	}

	/**
	 * save sightseeing, checks price category and city in database to not
	 * duplicated when creating sightseeing
	 */
	@Override
	public void saveSightseeing(Sightseeing sightseeing, String priceCategory,
			String city) {
		sightseeing.setOwner(profileService.getCurrentUser());
		sightseeing.setStatus(Status.PENDING);
		Integer pcId = priceCategoryRepository.findByPriceCategory(
				priceCategory).getPriceCategoryId();
		Integer cityId = cityRepository.findByCity(city).getCityId();
		sightseeing.setPriceCategory(priceCategoryRepository.findOne(pcId));
		sightseeing.setCity(cityRepository.findOne(cityId));
		sightseeingRepository.save(sightseeing);
	}

	/**
	 * update sightseeing, checks price category and city in database to not
	 * duplicated when editing sightseeing
	 */
	@Override
	public void updateSightseeing(Sightseeing sightseeing,
			String priceCategory, String city) {
		sightseeing.setOwner(profileService.getCurrentUser());
		Integer pcId = priceCategoryRepository.findByPriceCategory(
				priceCategory).getPriceCategoryId();
		Integer cityId = cityRepository.findByCity(city).getCityId();
		sightseeing.setImage(imageRepository.findAllByPlace(sightseeing));
		sightseeing.setPriceCategory(priceCategoryRepository.findOne(pcId));
		sightseeing.setCity(cityRepository.findOne(cityId));
		sightseeingRepository.save(sightseeing);
	}

	/**
	 * method for liking sightseeing
	 */
	@Override
	public void saveLikerforSightseing(User user, Sightseeing sightseeing) {
		Set<User> likers = userRepository.findByFavouriveSights(sightseeing);
		likers.add(user);
		Set<Sightseeing> favouriveSights = sightseeingRepository
				.findByLikers(user);
		sightseeing.setLikers(likers);
		favouriveSights.add(sightseeing);
		user.setFavouriveSights(favouriveSights);
		userRepository.save(user);
		sightseeingRepository.save(sightseeing);
	}

	/**
	 * method for checking is sightseeing is liked or not liked
	 */
	@Override
	public boolean favouriteCheck(Sightseeing sightseeing, User liker) {
		Set<User> likers = userRepository.findByFavouriveSights(sightseeing);
		return likers.contains(liker);
	}

	/**
	 * method for unliking sightseeing
	 */
	public void unlikeSightseeing(Integer id, User liker) {
		sightseeingRepository.unlike(id, liker.getUserId());
	}

	/**
	 * method return current sightseeing rating
	 */
	public Integer getCurrentRating(Integer id) {
		return sightseeingRepository.getRating(id);
	}

	/**
	 * Counts amount of pages
	 */
	public Long getSightseeingsPaging(Long size, String sender, User currentUser) {
		Long amount;
		if ("all-sightseeings".equals(sender)) {
			Long dataBaseSize = sightseeingRepository.count();
			if (dataBaseSize % size == 0) {
				amount = dataBaseSize / size;
			} else {
				amount = dataBaseSize / size + 1;
			}
		} else if ("favourite-sightseeings".equals(sender)) {
			Long dataFavouriteSize = (long) sightseeingRepository.findByLikers(
					currentUser).size();
			if (dataFavouriteSize % size == 0l) {
				amount = dataFavouriteSize / size;
			} else {
				amount = dataFavouriteSize / size + 1;
			}
		} else {
			Long dataOwnerSize = sightseeingRepository
					.countByOwner(currentUser);
			if (dataOwnerSize % size == 0) {
				amount = dataOwnerSize / size;
			} else {
				amount = dataOwnerSize / size + 1;
			}
		}
		return amount;
	}

	/**
	 * form PageRequest according to orderBy value
	 * 
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param orderType
	 * @return
	 */
	public PageRequest getPageRequest(Integer page, Integer size,
			String orderBy, String orderType) {
		if (orderType.equals("ASC")) {
			return new PageRequest(page - 1, size, Sort.Direction.ASC, orderBy);
		} else {
			return new PageRequest(page - 1, size, Sort.Direction.DESC, orderBy);
		}
	}
}
