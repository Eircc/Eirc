package com.ccc.eirc.commons.utils;

import com.ccc.eirc.commons.domain.EircConstant;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <a>Title:FileUtil</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 文件压缩下载 工具类
 *
 * @Author ccc
 * @Date 2020/3/23 18:05
 * @Version 1.0.0
 */
@Slf4j
public class FileUtil {

    private static final int BUFFER = 1024 * 8;

    /**
     * 压缩文件或目录
     *
     * @param formPath 待压缩文件或路径
     * @param toPath   压缩文件，如 xx.zip
     * @throws Exception
     */
    public static void compress(String formPath, String toPath) throws Exception {
        File formFile = new File(formPath);
        File toFile = new File(toPath);
        if (!formFile.exists()) {
            throw new FileNotFoundException(formPath + "不存在！");
        }
        try (FileOutputStream outputStream = new FileOutputStream(toFile);
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new CRC32());
             ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream);) {

            String baseDir = "";
            compress(formFile, zipOutputStream, baseDir);
        }
    }

    /**
     * 文件下载
     *
     * @param filePath 待下载文件路径
     * @param fileName 下载文件名称
     * @param delete   下载后是否删除源文件
     * @param response HttpServletResponse
     * @throws Exception Exception
     */
    public static void download(String filePath, String fileName, Boolean delete, HttpServletResponse response) throws Exception {
        File file = new File(filePath);
        if (!file.exists())
            throw new Exception("文件未找到，请确保文件存在");
        String fileType = getFileType(file);
        if (!fileTypeIsValid(fileType)) {
            throw new Exception("暂时不支持该文件类型下载");
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "utf-8"));
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        try (InputStream inputStream = new FileInputStream(file); OutputStream outputStream = response.getOutputStream()) {
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }
        } finally {
            {
                if (delete)
                    delete(filePath);
            }
        }
    }

    /**
     * 递归删除文件或目录
     *
     * @param filePath 文件或目录
     */
    private static void delete(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                Arrays.stream(files).forEach(f -> delete(f.getPath()));
        }
        file.delete();
    }

    /**
     * 校验文件类型是否是允许下载的类型
     *
     * @param fileType fileType
     * @return Boolean
     */
    private static Boolean fileTypeIsValid(String fileType) {
        Preconditions.checkNotNull(fileType);
        fileType = StringUtils.lowerCase(fileType);
        return ArrayUtils.contains(EircConstant.VALID_FILE_TYPE, fileType);
    }

    /**
     * 获取文件类型
     *
     * @param file 文件
     * @return 文件类型
     * @throws Exception Exception
     */
    private static String getFileType(File file) throws Exception {
        Preconditions.checkNotNull(file);
        if (file.isDirectory()) {
            throw new Exception("file不是文件");
        }
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    private static void compress(File file, ZipOutputStream zipOut, String baseDir) throws IOException {
        if (file.isDirectory()) {
            compressDirectory(file, zipOut, baseDir);
        } else {
            compressFile(file, zipOut, baseDir);
        }

    }

    private static void compressDirectory(File dir, ZipOutputStream zipOut, String baseDir) throws IOException {
        File[] files = dir.listFiles();
        if (files != null && ArrayUtils.isNotEmpty(files)) {
            for (File file : files) {
                compress(file, zipOut, baseDir + dir.getName() + "/");
            }
        }
    }

    private static void compressFile(File file, ZipOutputStream zipOut, String baseDir) throws IOException {
        if (!file.exists()) {
            return;
        }
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            ZipEntry entry = new ZipEntry(baseDir + file.getName());
            zipOut.putNextEntry(entry);
            int count;
            byte[] data = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                zipOut.write(data, 0, count);
            }
        }
    }
}
