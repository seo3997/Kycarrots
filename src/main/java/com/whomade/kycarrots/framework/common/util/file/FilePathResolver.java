package com.whomade.kycarrots.framework.common.util.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 파일 경로 리졸버 (동적 개선판)
 * YAML의 'file.paths' 하위에 정의된 모든 카테고리를 자동으로 인식합니다.
 * 신규 업무(카테고리) 추가 시 소스 수정 없이 YAML 설정만으로 동작합니다.
 */
@Component
@ConfigurationProperties(prefix = "file")
public class FilePathResolver {

    private Map<String, PathConfig> paths = new HashMap<>();
    private StorageConfig storage = new StorageConfig();
    private OciConfig oci = new OciConfig();

    // Getters and Setters for Spring Boot Binding
    public Map<String, PathConfig> getPaths() { return paths; }
    public void setPaths(Map<String, PathConfig> paths) { this.paths = paths; }
    public StorageConfig getStorage() { return storage; }
    public void setStorage(StorageConfig storage) { this.storage = storage; }
    public OciConfig getOci() { return oci; }
    public void setOci(OciConfig oci) { this.oci = oci; }

    public static class PathConfig {
        private String uploadDir;
        private String publicUrl;
        private String resourcePath;

        public String getUploadDir() { return uploadDir; }
        public void setUploadDir(String uploadDir) { this.uploadDir = uploadDir; }
        public String getPublicUrl() { return publicUrl; }
        public void setPublicUrl(String publicUrl) { this.publicUrl = publicUrl; }
        public String getResourcePath() { return resourcePath; }
        public void setResourcePath(String resourcePath) { this.resourcePath = resourcePath; }
    }

    public static class StorageConfig {
        private String type = "N";
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class OciConfig {
        private String bucketName;
        private String namespace;
        private String publicUrl;

        public String getBucketName() { return bucketName; }
        public void setBucketName(String bucketName) { this.bucketName = bucketName; }
        public String getNamespace() { return namespace; }
        public void setNamespace(String namespace) { this.namespace = namespace; }
        public String getPublicUrl() { return publicUrl; }
        public void setPublicUrl(String publicUrl) { this.publicUrl = publicUrl; }
    }

    public Storage resolve(String pathKey) {
        String baseKey = pathKey;
        String subPath = "";

        if (pathKey != null && pathKey.contains("/")) {
            int idx = pathKey.indexOf("/");
            baseKey = pathKey.substring(0, idx);
            subPath = pathKey.substring(idx + 1);
        }

        PathConfig config = paths.get(baseKey);
        if (config == null) {
            // Case-insensitive lookup fallback
            for (String key : paths.keySet()) {
                if (key.equalsIgnoreCase(baseKey)) {
                    config = paths.get(key);
                    break;
                }
            }
        }

        if (config != null) {
            String dir = config.getUploadDir();
            String url = config.getPublicUrl();
            String finalUrl = url;

            String storageType = storage.getType();
            if ("Y".equalsIgnoreCase(storageType) && oci.getPublicUrl() != null && !oci.getPublicUrl().isEmpty()) {
                finalUrl = oci.getPublicUrl();
            }

            if (!subPath.isEmpty()) {
                dir = Paths.get(dir, subPath).toString();
                // OCI가 아닐 때만(로컬일 때만) URL에 subPath를 추가. OCI는 pathPrefix가 전체 경로를 담당함.
                if (!"Y".equalsIgnoreCase(storageType)) {
                    finalUrl = ensureUrl(finalUrl) + subPath + "/";
                }
            }
            return new Storage(ensureDir(dir), ensureUrl(finalUrl), storageType, oci.getBucketName(), oci.getNamespace(), pathKey);
        }

        throw new IllegalArgumentException("Unknown pathKey: " + pathKey + ". 등록된 카테고리: " + paths.keySet());
    }

    public Storage getGlobalConfig() {
        return new Storage("", "", storage.getType(), oci.getBucketName(), oci.getNamespace(), "");
    }

    private String ensureDir(String dir) {
        if (dir == null || dir.isEmpty())
            return dir;
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
        private final String pathPrefix;

        public Storage(String uploadDir, String publicUrl, String storageType, String bucketName, String namespace, String pathPrefix) {
            this.uploadDir = uploadDir;
            if (publicUrl != null && !publicUrl.isEmpty() && !publicUrl.endsWith("/")) {
                this.publicUrl = publicUrl + "/";
            } else {
                this.publicUrl = publicUrl;
            }
            this.storageType = storageType;
            this.bucketName = bucketName;
            this.namespace = namespace;
            this.pathPrefix = pathPrefix;
        }

        public String getPathPrefix() {
            return (pathPrefix != null && !pathPrefix.isEmpty()) ? pathPrefix + "/" : "";
        }

        public String getUploadDir() { return uploadDir; }
        public String getPublicUrl() { return publicUrl; }
        public String getStorageType() { return storageType; }
        public String getBucketName() { return bucketName; }
        public String getNamespace() { return namespace; }
    }
}
