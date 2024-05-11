package com.csmtech.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	public static String uploadFile(MultipartFile multipartFile) {
		File f = null;
		try {
			// Ensure the target directory exists
//            File uploadDir = new File("d:/UploadFile/");
			File uploadDir = new File("D://LMS_ACTUAL_FILE/AUDIO/");
			uploadDir.mkdirs(); // Create directories if they don't exist

			// Sanitize the file name to prevent directory traversal
			String fileName = sanitizeFileName(multipartFile.getOriginalFilename());

			// Create the destination file
			f = new File(uploadDir, fileName);

			// Write the file using try-with-resources for resource management
			try (FileOutputStream fos = new FileOutputStream(f)) {
				byte[] b = multipartFile.getBytes();
				fos.write(b);
			}

			return f.getName();
		} catch (IOException e) {
			e.printStackTrace(); // Handle the exception appropriately (e.g., log it)
			return null; // Return an error indicator or message
		}
	}

	private static String sanitizeFileName(String fileName) {
		// Implement a sanitization mechanism to ensure the file name is safe
		// You can use regular expressions or other techniques to sanitize the name
		// For example, you can remove invalid characters or limit the length of the
		// name.
		// Ensure the sanitized name is unique if needed.
		// For simplicity, this example just returns the original file name.
		return fileName;
	}
}