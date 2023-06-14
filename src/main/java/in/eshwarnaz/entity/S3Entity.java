package in.eshwarnaz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="S3Object_Storage_Tbl")
public class S3Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer s3ObjectId;

	private String s3ObjectUrl;
}
