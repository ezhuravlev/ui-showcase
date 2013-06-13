package ru.ventra.recruitment.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Candidate")
public class Candidate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Size(min = 2, max = 50)
	@Column(name = "firstName", nullable = false, length = 50)
	private String firstName;

	@NotNull
	@Size(min = 2, max = 50)
	@Column(name = "lastName", nullable = false, length = 50)
	private String lastName;

	@Size(min = 2, max = 50)
	@Column(name = "middleName", length = 50)
	private String middleName;

	@Version
	@Column(name = "version")
	private Integer version;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , orphanRemoval = false)
	@JoinTable(name = "CandidateAttachment", joinColumns = { @JoinColumn(name = "candidateId") }, inverseJoinColumns = { @JoinColumn(name = "attachmentId") })
	private Set<Attachment> attachments = new HashSet<Attachment>();
	
	

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Integer getVersion() {
		return version;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}
}
