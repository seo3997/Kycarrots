package com.whomade.kycarrots.framework.common.util.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    /**
     * 파일을 지정된 기본 경로 + productId 하위 폴더에 저장하고, 저장된 File 객체를 반환한다.
     *
     * @param file         저장할 MultipartFile
     * @param baseDir      기본 디렉토리 경로 (예: C:/.../img/ad)
     * @param productId    하위 폴더 이름 (예: 123)
     * @return 저장된 File 객체
     * @throws IOException 폴더 생성 또는 파일 저장 실패 시
     */
    public static File saveFile(MultipartFile file, String baseDir, String productId) throws IOException {
        String targetDirPath = baseDir + File.separator + productId;
        File targetDir = new File(targetDirPath);

        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created) {
                throw new IOException("폴더 생성 실패: " + targetDirPath);
            }
        }

        String fileName = file.getOriginalFilename();
        File dest = new File(targetDir, fileName);
        file.transferTo(dest); // 실제 파일 저장

        return dest;
    }
}
