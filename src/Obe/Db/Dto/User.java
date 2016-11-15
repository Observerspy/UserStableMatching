package Obe.Db.Dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "lifespec")
@NamedQueries({
	@NamedQuery(name="getUser",query="from User")
})
public class User implements java.io.Serializable {

	// Fields

	private Integer unionId;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(Integer unionId) {
		this.unionId = unionId;
	}

	// Property accessors
	@Id
	@Column(name = "unionID", unique = true, nullable = false)
	public Integer getUnionId() {
		return this.unionId;
	}

	public void setUnionId(Integer unionId) {
		this.unionId = unionId;
	}

}