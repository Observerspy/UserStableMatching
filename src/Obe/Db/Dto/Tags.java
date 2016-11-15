package Obe.Db.Dto;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * Tags entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tags", catalog = "programmer")
@NamedQueries({
	@NamedQuery(name="getTags",query="from Tags as t where t.id.id = :id and t.id.source = :source")
})
public class Tags implements java.io.Serializable {

	// Fields

	private TagsId id;
	private String tagUrl;
	private String score;
	private String describtion;

	// Constructors

	/** default constructor */
	public Tags() {
	}

	/** full constructor */
	public Tags(TagsId id, String tagUrl, String score, String describtion) {
		this.id = id;
		this.tagUrl = tagUrl;
		this.score = score;
		this.describtion = describtion;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false, length = 200)),
			@AttributeOverride(name = "tagName", column = @Column(name = "tag_name", nullable = false, length = 200)),
			@AttributeOverride(name = "source", column = @Column(name = "source", nullable = false, length = 200)) })
	public TagsId getId() {
		return this.id;
	}

	public void setId(TagsId id) {
		this.id = id;
	}

	@Column(name = "tag_url", nullable = false, length = 200)
	public String getTagUrl() {
		return this.tagUrl;
	}

	public void setTagUrl(String tagUrl) {
		this.tagUrl = tagUrl;
	}

	@Column(name = "score", nullable = false, length = 200)
	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Column(name = "describtion", nullable = false, length = 16777215)
	public String getDescribtion() {
		return this.describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

}