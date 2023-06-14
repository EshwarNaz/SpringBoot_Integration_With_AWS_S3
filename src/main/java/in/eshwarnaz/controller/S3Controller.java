package in.eshwarnaz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

import in.eshwarnaz.util.S3Util;

@Controller
public class S3Controller {

	@Autowired
	private S3Util s3Util;
	
	@Autowired
	private AmazonS3 amazonS3Client;

	@GetMapping("/")
	public String loadForm() {
		return "upload";
	}

	@PostMapping("/upload")
	public String handleUploadForm(Model model, @RequestParam("file") MultipartFile multipart) {
		String message = "";
		String key="uploads/"+System.currentTimeMillis()+" "+multipart.getOriginalFilename();
		try {
			s3Util.saveFile(multipart);
			String url = amazonS3Client.getUrl("uploadfileintos3bucket", key).toString();
			s3Util.saveUrl(url);
			message = "Your file has been uploaded successfully!";
		} catch (Exception ex) {
			message = "Error uploading file: " + ex.getMessage();
		}
		model.addAttribute("message", message);
		return "message";
	}

}
