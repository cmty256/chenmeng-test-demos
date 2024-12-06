package com.chenmeng.project.xfyun.image_recognition.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 文件操作工具类
 *
 * @author chenmeng
 */
public class FileUtil {
	/**
	 * 读取文件内容为二进制数组
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] read(String filePath) throws IOException {

		InputStream in = new FileInputStream(filePath);
		byte[] data = inputStream2ByteArray(in);
		in.close();

		return data;
	}

	/**
	 * 流转二进制数组
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static byte[] inputStream2ByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}

	/**
	 * 保存文件
	 *
	 * @param filePath
	 * @param fileName
	 * @param content
	 */
	public static void save(String filePath, String fileName, byte[] content) {
		try {
			File filedir = new File(filePath);
			if (!filedir.exists()) {
				filedir.mkdirs();
			}
			File file = new File(filedir, fileName);
			OutputStream os = new FileOutputStream(file);
			os.write(content, 0, content.length);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String fileToBase64(String fileUrl) throws Exception {
	    // 使用 Java 8 引入的 Base64 类获取编码器，并将 URL 对象转换为字节数组后进行编码
		String url = new URL(fileUrl).getFile();
		String res = Base64.getEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));
		System.out.println("res = " + res);
		System.out.println("图片大小：" + res.length());
        if (res.length() > 1024 * 1024 * 4) {
            System.out.println("图片过大，请使用图片压缩工具压缩后重新上传：" + res.length());
        }
        return res;
	}

	public static void main(String[] args) {
		try {
			// 示例：将文件 URL 转换为 Base64 字符串
			String base64EncodedFile = fileToBase64("http://192.168.239.130:9000/my-file/test2.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ROOTNAME%2F20241205%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241205T091105Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=3486d698f9d297071158730472245c05970d1a7cb836e6f8b2b8fda4a6c91110");
			System.out.println(base64EncodedFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
