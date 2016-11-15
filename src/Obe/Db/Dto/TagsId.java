package Obe.Db.Dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TagsId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TagsId implements java.io.Serializable {

	// Fields

	private String id;
	private String tagName;
	private String source;

	// Constructors

	/** default constructor */
	public TagsId() {
	}

	/** full constructor */
	public TagsId(String id, String tagName, String source) {
		this.id = id;
		this.tagName = tagName;
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

	@Column(name = "tag_name", nullable = false, length = 200)
	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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
		if (!(other instanceof TagsId))
			return false;
		TagsId castOther = (TagsId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getTagName() == castOther.getTagName()) || (this
						.getTagName() != null && castOther.getTagName() != null && this
						.getTagName().equals(castOther.getTagName())))
				&& ((this.getSource() == castOther.getSource()) || (this
						.getSource() != null && castOther.getSource() != null && this
						.getSource().equals(castOther.getSource())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getTagName() == null ? 0 : this.getTagName().hashCode());
		result = 37 * result
				+ (getSource() == null ? 0 : this.getSource().hashCode());
		return result;
	}

}