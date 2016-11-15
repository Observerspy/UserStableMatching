package Obe.Db.Dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * Movie entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "movie", catalog = "lifespec")
@NamedQueries({
	@NamedQuery(name="getMovie",query="from Movie as m where m.mid = :id")
})
public class Movie implements java.io.Serializable {

	// Fields

	private Integer mid;
	private String name;
	private String director;
	private String scriptwriter;
	private String mainPlayers;
	private String categories;
	private String productCountry;
	private String duration;
	private String releasedTime;
	private String tags;
	private Double ratingCount;
	private Double star5;
	private Double star4;
	private Double star3;
	private Double star2;
	private Double star1;
	private String language;

	// Constructors

	/** default constructor */
	public Movie() {
	}

	/** minimal constructor */
	public Movie(Integer mid) {
		this.mid = mid;
	}

	/** full constructor */
	public Movie(Integer mid, String name, String director,
			String scriptwriter, String mainPlayers, String categories,
			String productCountry, String duration, String releasedTime,
			String tags, Double ratingCount, Double star5, Double star4,
			Double star3, Double star2, Double star1, String language) {
		this.mid = mid;
		this.name = name;
		this.director = director;
		this.scriptwriter = scriptwriter;
		this.mainPlayers = mainPlayers;
		this.categories = categories;
		this.productCountry = productCountry;
		this.duration = duration;
		this.releasedTime = releasedTime;
		this.tags = tags;
		this.ratingCount = ratingCount;
		this.star5 = star5;
		this.star4 = star4;
		this.star3 = star3;
		this.star2 = star2;
		this.star1 = star1;
		this.language = language;
	}

	// Property accessors
	@Id
	@Column(name = "mid", unique = true, nullable = false)
	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "director")
	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Column(name = "scriptwriter", length = 2000)
	public String getScriptwriter() {
		return this.scriptwriter;
	}

	public void setScriptwriter(String scriptwriter) {
		this.scriptwriter = scriptwriter;
	}

	@Column(name = "mainPlayers", length = 2000)
	public String getMainPlayers() {
		return this.mainPlayers;
	}

	public void setMainPlayers(String mainPlayers) {
		this.mainPlayers = mainPlayers;
	}

	@Column(name = "categories")
	public String getCategories() {
		return this.categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	@Column(name = "productCountry")
	public String getProductCountry() {
		return this.productCountry;
	}

	public void setProductCountry(String productCountry) {
		this.productCountry = productCountry;
	}

	@Column(name = "duration")
	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "releasedTime")
	public String getReleasedTime() {
		return this.releasedTime;
	}

	public void setReleasedTime(String releasedTime) {
		this.releasedTime = releasedTime;
	}

	@Column(name = "tags", length = 10000)
	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Column(name = "ratingCount", precision = 22, scale = 0)
	public Double getRatingCount() {
		return this.ratingCount;
	}

	public void setRatingCount(Double ratingCount) {
		this.ratingCount = ratingCount;
	}

	@Column(name = "star5", precision = 22, scale = 0)
	public Double getStar5() {
		return this.star5;
	}

	public void setStar5(Double star5) {
		this.star5 = star5;
	}

	@Column(name = "star4", precision = 22, scale = 0)
	public Double getStar4() {
		return this.star4;
	}

	public void setStar4(Double star4) {
		this.star4 = star4;
	}

	@Column(name = "star3", precision = 22, scale = 0)
	public Double getStar3() {
		return this.star3;
	}

	public void setStar3(Double star3) {
		this.star3 = star3;
	}

	@Column(name = "star2", precision = 22, scale = 0)
	public Double getStar2() {
		return this.star2;
	}

	public void setStar2(Double star2) {
		this.star2 = star2;
	}

	@Column(name = "star1", precision = 22, scale = 0)
	public Double getStar1() {
		return this.star1;
	}

	public void setStar1(Double star1) {
		this.star1 = star1;
	}

	@Column(name = "language")
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}