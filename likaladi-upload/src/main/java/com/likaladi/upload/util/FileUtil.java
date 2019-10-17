package com.likaladi.upload.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.likaladi.upload.entity.FileInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author likaladi
 * 文件工具类
 */
public class FileUtil {

	/**
	 * 通过MultipartFile获取file信息
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static FileInfo getFileInfo(MultipartFile file) throws Exception{

		String md5 = fileMd5(file.getInputStream());

		FileInfo fileInfo = new FileInfo();
		fileInfo.setId(md5);// 将文件的md5设置为文件表的id
		fileInfo.setName(file.getOriginalFilename());
		fileInfo.setContentType(file.getContentType());
		fileInfo.setIsimg(fileInfo.getContentType().startsWith("image/"));
		fileInfo.setSize(file.getSize());
		fileInfo.setCreateTime(new Date());

		return fileInfo;
	}

	/**
	 * 文件的md5
	 *
	 * @param inputStream
	 * @return
	 */
	public static String fileMd5(InputStream inputStream) {
		try {
			return DigestUtils.md5Hex(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 将文件保存到本地
	 *
	 * @param file
	 * @param path
	 * @return
	 */
	public static String saveFile(MultipartFile file, String path) {
		try {
			File targetFile = new File(path);
			if (targetFile.exists()) {
				return path;
			}

			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			file.transferTo(targetFile);

			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 删除本地文件
	 *
	 * @param pathname
	 * @return
	 */
	public static boolean deleteFile(String pathname) {
		File file = new File(pathname);
		if (file.exists()) {
			boolean flag = file.delete();

			if (flag) {
				File[] files = file.getParentFile().listFiles();
				if (files == null || files.length == 0) {
					file.getParentFile().delete();
				}
			}

			return flag;
		}

		return false;
	}
}
