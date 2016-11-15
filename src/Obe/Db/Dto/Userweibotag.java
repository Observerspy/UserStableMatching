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
 * Userweibotag entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "userweibotag", catalog = "lifespec")
@NamedQueries({
	@NamedQuery(name="getAllWeiboUser",query="select uw.id.unionId from Userweibotag as uw")
})
public class Userweibotag implements java.io.Serializable {

	// Fields

	private UserweibotagId id;

	// Constructors

	/** default constructor */
	public Userweibotag() {
	}

	/** full constructor */
	public Userweibotag(UserweibotagId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "unionId", column = @Column(name = "unionId", nullable = false)),
			@AttributeOverride(name = "tagDes", column = @Column(name = "tagDes", nullable = false)) })
	public UserweibotagId getId() {
		return this.id;
	}

	public void setId(UserweibotagId id) {
		this.id = id;
	}

}