package com.whomade.kycarrots.framework.common.util.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    /**
     * 파일을 지정된 기본 경로 + productId 하위 폴더에 저장한다.
     *
     * @param file         저장할 MultipartFile
     * @param baseDir      기본 디렉토리 경로
     * @param productId    하위 폴더 이름
     * @return 저장된 File 객체
     * @throws IOException 폴더 생성 또는 파일 저장 실패 시
     */
    public static File saveFile(MultipartFile file, String baseDir, String productId) throws IOException {
        return saveFile(file, baseDir, productId, file.getOriginalFilename());
    }

    /**
     * 파일을 지정된 기본 경로 + productId 하위 폴더에 지정된 파일명으로 저장한다.
     *
     * @param file           저장할 MultipartFile
     * @param baseDir        기본 디렉토리 경로
     * @param productId      하위 폴더 이름
     * @param customFileName 저장할 파일명
     * @return 저장된 File 객체
     * @throws IOException 폴더 생성 또는 파일 저장 실패 시
     */
    public static File saveFile(MultipartFile file, String baseDir, String productId, String customFileName) throws IOException {
        String targetDirPath = baseDir + File.separator + productId;
        File targetDir = new File(targetDirPath);

        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created) {
                throw new IOException("폴더 생성 실패: " + targetDirPath);
            }
        }

        File dest = new File(targetDir, customFileName);
        file.transferTo(dest); // 실제 파일 저장

        return dest;
    }

    public static boolean deleteFile(String baseDir, String productId, String fileName) {
        if (baseDir == null || productId == null || fileName == null) {
            return false;
        }

        String targetPath = baseDir + File.separator + productId + File.separator + fileName;
        File file = new File(targetPath);

        if (file.exists()) {
            return file.delete();
        }

        return true; // 파일이 이미 없는 경우는 성공으로 간주
    }
}
