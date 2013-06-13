package ru.ventra.recruitment.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-02-08T15:05:51.506+0400")
@StaticMetamodel(Attachment.class)
public class Attachment_ {
	public static volatile SingularAttribute<Attachment, Long> id;
	public static volatile SingularAttribute<Attachment, String> title;
	public static volatile SingularAttribute<Attachment, String> description;
	public static volatile SingularAttribute<Attachment, Integer> fileSize;
	public static volatile SingularAttribute<Attachment, String> mimeType;
	public static volatile SingularAttribute<Attachment, User> createdBy;
	public static volatile SingularAttribute<Attachment, Date> creationDate;
	public static volatile SingularAttribute<Attachment, User> modifiedBy;
	public static volatile SingularAttribute<Attachment, Date> modificationDate;
	public static volatile SingularAttribute<Attachment, AttachmentData> data;
	public static volatile SingularAttribute<Attachment, Integer> version;
}
