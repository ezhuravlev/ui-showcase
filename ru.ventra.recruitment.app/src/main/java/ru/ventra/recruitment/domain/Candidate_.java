package ru.ventra.recruitment.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-02-08T18:57:37.358+0400")
@StaticMetamodel(Candidate.class)
public class Candidate_ {
	public static volatile SingularAttribute<Candidate, Long> id;
	public static volatile SingularAttribute<Candidate, String> firstName;
	public static volatile SingularAttribute<Candidate, String> lastName;
	public static volatile SingularAttribute<Candidate, String> middleName;
	public static volatile SetAttribute<Candidate, Attachment> attachments;
	public static volatile SingularAttribute<Candidate, Integer> version;
}
