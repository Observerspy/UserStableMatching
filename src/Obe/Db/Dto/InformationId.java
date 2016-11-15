package Obe.Db.Dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * InformationId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class InformationId implements java.io.Serializable {

	// Fields

	private String id;
	private String userName;
	private String source;

	// Constructors

	/** default constructor */
	public InformationId() {
	}

	/** full constructor */
	public InformationId(String id, String userName, String source) {
		this.id = id;
		this.userName = userName;
		this.source = source;
	}

	// Property accessors

	@Column(name = "ID", nullable = false, length = 200)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "user_name", nullable = false, length = 200)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "source", nullable = false, length = 200)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof InformationId))
			return false;
		InformationId castOther = (InformationId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getUserName() == castOther.getUserName()) || (this
						.getUserName() != null
						&& castOther.getUserName() != null && this
						.getUserName().equals(castOther.getUserName())))
				&& ((this.getSource() == castOther.getSource()) || (this
						.getSource() != null && castOther.getSource() != null && this
						.getSource().equals(castOther.getSource())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getUserName() == null ? 0 : this.getUserName().hashCode());
		result = 37 * result
				+ (getSource() == null ? 0 : this.getSource().hashCode());
		return result;
	}

}