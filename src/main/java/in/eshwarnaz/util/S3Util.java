package in.eshwarnaz.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;

import in.eshwarnaz.entity.S3Entity;
import in.eshwarnaz.repo.S3ObjectRepo;

@Service
public class S3Util {

	@Value("${bucketName}")
	private String bucketName;

	@Autowired
	private final AmazonS3 s3;

	@Autowired
	private S3ObjectRepo s3Repo;

	public S3Util(AmazonS3 s3) {
		this.s3 = s3;
	}

	public String saveUrl(String url) {
		S3Entity entity = new S3Entity();
		entity.setS3ObjectUrl(url);
		s3Repo.save(entity);
		return "Object Saved";
	}

	public String saveFile(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		try {
			File file1 = convertMultiPartToFile(file);
			PutObjectResult putObjectResult = s3.putObject(bucketName, originalFilename, file1);
			return putObjectResult.getContentMd5();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}
