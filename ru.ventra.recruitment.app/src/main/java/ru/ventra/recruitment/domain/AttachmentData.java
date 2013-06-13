package ru.ventra.recruitment.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AttachmentData")
public class AttachmentData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne
	@JoinColumn(name = "id", referencedColumnName = "id")
	Attachment attachment;
	
	@Lob
	@NotNull
	@Column(name = "value", nullable = false)
	private byte[] value;
    
    
    
    public AttachmentData() {
    	
	}
    
    AttachmentData(Attachment attachment) {
		this.attachment = attachment;
	}

	byte[] getValue() {
		return value;
	}

	void setValue(byte[] value) {
		this.value = value;
	}
}
