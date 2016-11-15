package Obe.Db.Dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UsermovieId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class UsermovieId implements java.io.Serializable {

	// Fields

	private Integer unionId;
	private Integer mid;

	// Constructors

	/** default constructor */
	public UsermovieId() {
	}

	/** full constructor */
	public UsermovieId(Integer unionId, Integer mid) {
		this.unionId = unionId;
		this.mid = mid;
	}

	// Property accessors

	@Column(name = "unionID", nullable = false)
	public Integer getUnionId() {
		return this.unionId;
	}

	public void setUnionId(Integer unionId) {
		this.unionId = unionId;
	}

	@Column(name = "mid", nullable = false)
	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UsermovieId))
			return false;
		UsermovieId castOther = (UsermovieId) other;

		return ((this.getUnionId() == castOther.getUnionId()) || (this
				.getUnionId() != null && castOther.getUnionId() != null && this
				.getUnionId().equals(castOther.getUnionId())))
				&& ((this.getMid() == castOther.getMid()) || (this.getMid() != null
						&& castOther.getMid() != null && this.getMid().equals(
						castOther.getMid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUnionId() == null ? 0 : this.getUnionId().hashCode());
		result = 37 * result
				+ (getMid() == null ? 0 : this.getMid().hashCode());
		return result;
	}

}