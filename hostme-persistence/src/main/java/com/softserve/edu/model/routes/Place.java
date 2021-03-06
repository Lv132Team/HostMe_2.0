package com.softserve.edu.model.routes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softserve.edu.model.*;

import javax.persistence.*;

import java.util.List;
import java.util.Set;
import com.google.common.base.Objects;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Place {

	@Id
	@SequenceGenerator(name = "place_id_seq", sequenceName = "place_id_seq", allocationSize = 111)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_id_seq")
	@Column
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	@Column
	private String comment;

	@Column
	private String website;

	@OneToMany(mappedBy = "place", fetch = FetchType.EAGER,orphanRemoval = true)
	private Set<Image> image;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private PriceCategory priceCategory;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private City city;

	@Column
	private String address;

	@ManyToMany(mappedBy = "places")
	Set<Route> routes;

	@ManyToOne
	private User owner;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "attendee")
	private Set<User> attendee;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "favouriveSights")
	private Set<User> likers;

	@Column
	private Integer rating;

	@OneToMany(mappedBy = "place", fetch = FetchType.LAZY, cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE })
	private List<Post> posts;

	@ManyToMany(mappedBy = "bookedPlaces")
	private Set<User> bookedBy;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@JsonIgnore
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonIgnore
	public Set<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}

	@JsonIgnore
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Set<User> getAttendee() {
		return attendee;
	}

	public void setAttendee(Set<User> attendee) {
		this.attendee = attendee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Set<Image> getImage() {
		return image;
	}

	public void setImage(Set<Image> image) {
		this.image = image;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public PriceCategory getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(PriceCategory priceCategory) {
		this.priceCategory = priceCategory;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Set<User> getLikers() {
		return likers;
	}

	public void setLikers(Set<User> likers) {
		this.likers = likers;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Set<User> getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(Set<User> bookedBy) {
		this.bookedBy = bookedBy;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, name, description, comment, website, image,
				status, priceCategory, city, address, routes, owner, attendee,
				likers, rating, posts, bookedBy);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Place) {
			Place that = (Place) object;
			return Objects.equal(this.id, that.id)
					&& Objects.equal(this.name, that.name)
					&& Objects.equal(this.description, that.description)
					&& Objects.equal(this.comment, that.comment)
					&& Objects.equal(this.website, that.website)
					&& Objects.equal(this.image, that.image)
					&& Objects.equal(this.status, that.status)
					&& Objects.equal(this.priceCategory, that.priceCategory)
					&& Objects.equal(this.city, that.city)
					&& Objects.equal(this.address, that.address)
					&& Objects.equal(this.routes, that.routes)
					&& Objects.equal(this.owner, that.owner)
					&& Objects.equal(this.attendee, that.attendee)
					&& Objects.equal(this.likers, that.likers)
					&& Objects.equal(this.rating, that.rating)
					&& Objects.equal(this.posts, that.posts)
					&& Objects.equal(this.bookedBy, that.bookedBy);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", description="
				+ description + ", comment=" + comment + ", website=" + website
				+ ", image=" + image + ", status=" + status
				+ ", priceCategory=" + priceCategory + ", city=" + city
				+ ", address=" + address + ", routes=" + routes + ", owner="
				+ owner + ", attendee=" + attendee + ", likers=" + likers
				+ ", rating=" + rating + ", posts=" + posts + ", bookedBy="
				+ bookedBy + "]";
	}
	
}
