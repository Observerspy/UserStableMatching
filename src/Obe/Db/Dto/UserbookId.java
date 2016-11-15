package Obe.Db.Dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserbookId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class UserbookId implements java.io.Serializable {

	// Fields

	private Integer unionId;
	private Integer bid;

	// Constructors

	/** default constructor */
	public UserbookId() {
	}

	/** full constructor */
	public UserbookId(Integer unionId, Integer bid) {
		this.unionId = unionId;
		this.bid = bid;
	}

	// Property accessors

	@Column(name = "unionID", nullable = false)
	public Integer getUnionId() {
		return this.unionId;
	}

	public void setUnionId(Integer unionId) {
		this.unionId = unionId;
	}

	@Column(name = "bid", nullable = false)
	public Integer getBid() {
		return this.bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserbookId))
			return false;
		UserbookId castOther = (UserbookId) other;

		return ((this.getUnionId() == castOther.getUnionId()) || (this
				.getUnionId() != null && castOther.getUnionId() != null && this
				.getUnionId().equals(castOther.getUnionId())))
				&& ((this.getBid() == castOther.getBid()) || (this.getBid() != null
						&& castOther.getBid() != null && this.getBid().equals(
						castOther.getBid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUnionId() == null ? 0 : this.getUnionId().hashCode());
		result = 37 * result
				+ (getBid() == null ? 0 : this.getBid().hashCode());
		return result;
	}

}