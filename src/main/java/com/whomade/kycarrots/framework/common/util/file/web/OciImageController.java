package com.whomade.kycarrots.framework.common.util.file.web;

import com.whomade.kycarrots.framework.common.util.file.FilePathResolver;
import com.whomade.kycarrots.framework.common.util.file.OciObjectStorageService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class OciImageController {

    @Resource
    private FilePathResolver resolver;

    @Resource
    private OciObjectStorageService ociService;

    @RequestMapping("/common/img/{pathKey}/{dateFolder}/{filename:.+}")
    public void serveImage(
            @PathVariable String pathKey,
            @PathVariable String dateFolder,
            @PathVariable("filename") String filename,
            HttpServletResponse response) {

        FilePathResolver.Storage storage = resolver.resolve(pathKey);

        if ("Y".equalsIgnoreCase(storage.getStorageType())) {
            String objectName = dateFolder + "/" + filename;
            try (InputStream is = ociService.getFile(storage.getNamespace(), storage.getBucketName(), objectName);
                 OutputStream os = response.getOutputStream()) {
                
                String contentType = getContentType(filename);
                response.setContentType(contentType);
                
                FileCopyUtils.copy(is, os);
                os.flush();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            // 로컬인 경우 WebConfig의 ResourceHandler가 처리하도록 404 혹은 무시 유도
            // 하지만 이 컨트롤러가 잡았다면 직접 서빙하거나 가만히 있어야 함.
            // 여기서는 단순성을 위해 그냥 404 리턴 (로컬은 WebConfig가 우선순위가 높도록 설정하는 것이 좋음)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String getContentType(String filename) {
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        switch (ext) {
            case "jpg":
            case "jpeg": return "image/jpeg";
            case "png": return "image/png";
            case "gif": return "image/gif";
            case "webp": return "image/webp";
            default: return "application/octet-stream";
        }
    }
}
