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
 * Information entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "information", catalog = "programmer")
@NamedQueries({
	@NamedQuery(name="getInfor",query="from Information as i where i.id.id = :id")
})
public class Information implements java.io.Serializable {

	// Fields

	private InformationId id;
	private String describtion;
	private String topPost;

	// Constructors

	/** default constructor */
	public Information() {
	}

	/** full constructor */
	public Information(InformationId id, String describtion, String topPost) {
		this.id = id;
		this.describtion = describtion;
		this.topPost = topPost;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false, length = 200)),
			@AttributeOverride(name = "userName", column = @Column(name = "user_name", nullable = false, length = 200)),
			@AttributeOverride(name = "source", column = @Column(name = "source", nullable = false, length = 200)) })
	public InformationId getId() {
		return this.id;
	}

	public void setId(InformationId id) {
		this.id = id;
	}

	@Column(name = "describtion", nullable = false, length = 16777215)
	public String getDescribtion() {
		return this.describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	@Column(name = "top_post", nullable = false, length = 16777215)
	public String getTopPost() {
		return this.topPost;
	}

	public void setTopPost(String topPost) {
		this.topPost = topPost;
	}

}