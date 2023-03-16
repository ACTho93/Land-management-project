package vn.edu.vinaenter.util;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	public static boolean isListFileNameOK(List<MultipartFile> listFile) {
		for (MultipartFile multipartFile : listFile) {
			if("".equals(multipartFile.getOriginalFilename())) {
				return false;
			}
		}
		return true;
	}
	
	public static String rename(String fileName) {
		return FilenameUtils.getBaseName(fileName) + "-" + System.nanoTime() + "." + FilenameUtils.getExtension(fileName); // đổi tên file
	}
}
