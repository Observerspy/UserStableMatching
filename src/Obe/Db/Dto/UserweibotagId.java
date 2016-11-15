package Obe.Db.Dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserweibotagId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class UserweibotagId implements java.io.Serializable {

	// Fields

	private Integer unionId;
	private String tagDes;

	// Constructors

	/** default constructor */
	public UserweibotagId() {
	}

	/** full constructor */
	public UserweibotagId(Integer unionId, String tagDes) {
		this.unionId = unionId;
		this.tagDes = tagDes;
	}

	// Property accessors

	@Column(name = "unionId", nullable = false)
	public Integer getUnionId() {
		return this.unionId;
	}

	public void setUnionId(Integer unionId) {
		this.unionId = unionId;
	}

	@Column(name = "tagDes", nullable = false)
	public String getTagDes() {
		return this.tagDes;
	}

	public void setTagDes(String tagDes) {
		this.tagDes = tagDes;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserweibotagId))
			return false;
		UserweibotagId castOther = (UserweibotagId) other;

		return ((this.getUnionId() == castOther.getUnionId()) || (this
				.getUnionId() != null && castOther.getUnionId() != null && this
				.getUnionId().equals(castOther.getUnionId())))
				&& ((this.getTagDes() == castOther.getTagDes()) || (this
						.getTagDes() != null && castOther.getTagDes() != null && this
						.getTagDes().equals(castOther.getTagDes())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUnionId() == null ? 0 : this.getUnionId().hashCode());
		result = 37 * result
				+ (getTagDes() == null ? 0 : this.getTagDes().hashCode());
		return result;
	}

}