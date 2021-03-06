package Obe.Db.Dto;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * Userbook entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "userbook", catalog = "lifespec")
@NamedQueries({
	@NamedQuery(name="getBookUser",query="from Userbook as ub where ub.id.unionId = :id")
})
public class Userbook implements java.io.Serializable {

	// Fields

	private UserbookId id;
	private Timestamp timeStamp;
	private Integer star;
	private String comment;
	private String tags;

	// Constructors

	/** default constructor */
	public Userbook() {
	}

	/** minimal constructor */
	public Userbook(UserbookId id) {
		this.id = id;
	}

	/** full constructor */
	public Userbook(UserbookId id, Timestamp timeStamp, Integer star,
			String comment, String tags) {
		this.id = id;
		this.timeStamp = timeStamp;
		this.star = star;
		this.comment = comment;
		this.tags = tags;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "unionId", column = @Column(name = "unionID", nullable = false)),
			@AttributeOverride(name = "bid", column = @Column(name = "bid", nullable = false)) })
	public UserbookId getId() {
		return this.id;
	}

	public void setId(UserbookId id) {
		this.id = id;
	}

	@Column(name = "timeStamp", length = 19)
	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Column(name = "star")
	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	@Column(name = "comment", length = 10000)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "tags", length = 10000)
	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}