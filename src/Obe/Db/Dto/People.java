package Obe.Db.Dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * People entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "people", catalog = "programmer")
@NamedQueries({
	@NamedQuery(name="getPeople",query="from People")
})
public class People implements java.io.Serializable {

	// Fields

	private String id;
	private String programmerId;
	private String programmerUrl;
	private String stackexchangeId;
	private String stackexchangeUrl;
	private String superuserId;
	private String superuserUrl;

	// Constructors

	/** default constructor */
	public People() {
	}

	/** full constructor */
	public People(String id, String programmerId, String programmerUrl,
			String stackexchangeId, String stackexchangeUrl,
			String superuserId, String superuserUrl) {
		this.id = id;
		this.programmerId = programmerId;
		this.programmerUrl = programmerUrl;
		this.stackexchangeId = stackexchangeId;
		this.stackexchangeUrl = stackexchangeUrl;
		this.superuserId = superuserId;
		this.superuserUrl = superuserUrl;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 200)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "programmerID", nullable = false, length = 200)
	public String getProgrammerId() {
		return this.programmerId;
	}

	public void setProgrammerId(String programmerId) {
		this.programmerId = programmerId;
	}

	@Column(name = "programmerURL", nullable = false, length = 200)
	public String getProgrammerUrl() {
		return this.programmerUrl;
	}

	public void setProgrammerUrl(String programmerUrl) {
		this.programmerUrl = programmerUrl;
	}

	@Column(name = "stackexchangeID", nullable = false, length = 200)
	public String getStackexchangeId() {
		return this.stackexchangeId;
	}

	public void setStackexchangeId(String stackexchangeId) {
		this.stackexchangeId = stackexchangeId;
	}

	@Column(name = "stackexchangeURL", nullable = false, length = 200)
	public String getStackexchangeUrl() {
		return this.stackexchangeUrl;
	}

	public void setStackexchangeUrl(String stackexchangeUrl) {
		this.stackexchangeUrl = stackexchangeUrl;
	}

	@Column(name = "superuserID", nullable = false, length = 200)
	public String getSuperuserId() {
		return this.superuserId;
	}

	public void setSuperuserId(String superuserId) {
		this.superuserId = superuserId;
	}

	@Column(name = "superuserURL", nullable = false, length = 200)
	public String getSuperuserUrl() {
		return this.superuserUrl;
	}

	public void setSuperuserUrl(String superuserUrl) {
		this.superuserUrl = superuserUrl;
	}

}