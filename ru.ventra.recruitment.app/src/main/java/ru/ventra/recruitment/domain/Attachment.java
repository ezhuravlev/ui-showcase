package ru.ventra.recruitment.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "Attachment")
public class Attachment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "fileSize", nullable = false)
	private Integer fileSize;
	
	@Column(name = "mimeType", nullable = false)
	private String mimeType;

	@ManyToOne(optional = false)
	@JoinColumn(name = "createdBy", referencedColumnName = "id", nullable = false)
	private User createdBy;	

	@Column(name = "creationDate", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "modifiedBy", referencedColumnName = "id", nullable = false)
	private User modifiedBy;
	
	@Column(name = "modificationDate", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date modificationDate;
	
	@OneToOne(mappedBy = "attachment", cascade = CascadeType.ALL)
    private AttachmentData data = new AttachmentData(this);

	@Version
	@Column(name = "version")
	private Integer version;
	
	

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public byte[] getValue() {
		return data.getValue();
	}

	public void setValue(byte[] value) {
		data.setValue(value);
	}

	public Integer getVersion() {
		return version;
	}
}
