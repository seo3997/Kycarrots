package com.whomade.kycarrots.framework.common.util.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.file.Paths;

@Component
public class FilePathResolver {

    @Value("${file.product.upload-dir}")
    private String productUploadDir;

    @Value("${file.product.public-url}")
    private String productPublicUrl;

    @Value("${file.board.upload-dir}")
    private String boardUploadDir;

    @Value("${file.board.public-url}")
    private String boardPublicUrl;

    @Value("${file.storage.type:N}")
    private String storageType;

    @Value("${file.oci.bucket-name:}")
    private String bucketName;

    @Value("${file.oci.namespace:}")
    private String namespace;

    public Storage resolve(String pathKey) {
        String baseKey = pathKey;
        String subPath = "";

        if (pathKey != null && pathKey.contains("/")) {
            int idx = pathKey.indexOf("/");
            baseKey = pathKey.substring(0, idx);
            subPath = pathKey.substring(idx + 1);
        }

        String dir = null;
        String url = null;

        if ("product".equalsIgnoreCase(baseKey)) {
            dir = productUploadDir;
            url = productPublicUrl;
        } else if ("board".equalsIgnoreCase(baseKey)) {
            dir = boardUploadDir;
            url = boardPublicUrl;
        }

        if (dir != null) {
            if (!subPath.isEmpty()) {
                // 하위 경로가 있으면 OS별 경로 구분자를 처리하여 결합
                dir = Paths.get(dir, subPath).toString();
                // URL도 하위 경로를 포함하도록 결합
                url = ensureUrl(url) + subPath + "/";
            }
            return new Storage(ensureDir(dir), ensureUrl(url), storageType, bucketName, namespace);
        }

        throw new IllegalArgumentException("Unknown pathKey: " + pathKey + " (root must be 'product' or 'board')");
    }

    public Storage getGlobalConfig() {
        return new Storage("", "", storageType, bucketName, namespace);
    }

    private String ensureDir(String dir) {
        if (dir == null || dir.isEmpty())
            return dir;
        // Windows/Unix 모두 안전: 마지막 구분자 강제 제거 (Paths로 합칠 것이므로)
        if (dir.endsWith("/") || dir.endsWith("\\")) {
            return dir.substring(0, dir.length() - 1);
        }
        return dir;
    }

    private String ensureUrl(String url) {
        if (url == null || url.isEmpty())
            return url;
        return url.endsWith("/") ? url : (url + "/");
    }

    public static class Storage {
        private final String uploadDir;
        private final String publicUrl;
        private final String storageType;
        private final String bucketName;
        private final String namespace;

        public Storage(String uploadDir, String publicUrl, String storageType, String bucketName, String namespace) {
            this.uploadDir = uploadDir;
            this.publicUrl = publicUrl;
            this.storageType = storageType;
            this.bucketName = bucketName;
            this.namespace = namespace;
        }

        public String getUploadDir() {
            return uploadDir;
        }

        public String getPublicUrl() {
            return publicUrl;
        }

        public String getStorageType() {
            return storageType;
        }

        public String getBucketName() {
            return bucketName;
        }

        public String getNamespace() {
            return namespace;
        }
    }
}
