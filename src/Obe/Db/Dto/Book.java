package Obe.Db.Dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * Book entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "book", catalog = "lifespec")
@NamedQueries({
	@NamedQuery(name="getBook",query="from Book as b where b.bid = :id")
})
public class Book implements java.io.Serializable {

	// Fields

	private Integer bid;
	private String name;
	private String writer;
	private String publisher;
	private String publishTimeStamp;
	private Integer pageCount;
	private Double cost;
	private String layout;
	private String tags;
	private Double ratingCount;
	private Double star5;
	private Double star4;
	private Double star3;
	private Double star2;
	private Double star1;

	// Constructors

	/** default constructor */
	public Book() {
	}

	/** minimal constructor */
	public Book(Integer bid) {
		this.bid = bid;
	}

	/** full constructor */
	public Book(Integer bid, String name, String writer, String publisher,
			String publishTimeStamp, Integer pageCount, Double cost,
			String layout, String tags, Double ratingCount, Double star5,
			Double star4, Double star3, Double star2, Double star1) {
		this.bid = bid;
		this.name = name;
		this.writer = writer;
		this.publisher = publisher;
		this.publishTimeStamp = publishTimeStamp;
		this.pageCount = pageCount;
		this.cost = cost;
		this.layout = layout;
		this.tags = tags;
		this.ratingCount = ratingCount;
		this.star5 = star5;
		this.star4 = star4;
		this.star3 = star3;
		this.star2 = star2;
		this.star1 = star1;
	}

	// Property accessors
	@Id
	@Column(name = "bid", unique = true, nullable = false)
	public Integer getBid() {
		return this.bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "writer")
	public String getWriter() {
		return this.writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Column(name = "publisher")
	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Column(name = "publishTimeStamp")
	public String getPublishTimeStamp() {
		return this.publishTimeStamp;
	}

	public void setPublishTimeStamp(String publishTimeStamp) {
		this.publishTimeStamp = publishTimeStamp;
	}

	@Column(name = "pageCount")
	public Integer getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	@Column(name = "cost", precision = 255, scale = 0)
	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Column(name = "layout")
	public String getLayout() {
		return this.layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
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

}