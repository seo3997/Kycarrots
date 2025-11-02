package com.whomade.kycarrots.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

@Component
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    // file.max-size-total, file.max-size-each
    private DataSize maxSizeTotal = DataSize.ofMegabytes(50);
    private DataSize maxSizeEach  = DataSize.ofMegabytes(10);

    private Storage product = new Storage();
    private Storage board   = new Storage();

    public static class Storage {
        /** 예: /var/www/uploads/product */
        private String uploadDir;
        /** 예: http://host:9000/common/img/product */
        private String publicUrl;
        /** 예: file:/var/www/uploads/product/ */
        private String resourcePath;

        // getters/setters
        public String getUploadDir() { return uploadDir; }
        public void setUploadDir(String uploadDir) { this.uploadDir = uploadDir; }

        public String getPublicUrl() { return publicUrl; }
        public void setPublicUrl(String publicUrl) { this.publicUrl = publicUrl; }

        public String getResourcePath() { return resourcePath; }
        public void setResourcePath(String resourcePath) { this.resourcePath = resourcePath; }
    }

    // getters/setters
    public DataSize getMaxSizeTotal() { return maxSizeTotal; }
    public void setMaxSizeTotal(DataSize maxSizeTotal) { this.maxSizeTotal = maxSizeTotal; }

    public DataSize getMaxSizeEach() { return maxSizeEach; }
    public void setMaxSizeEach(DataSize maxSizeEach) { this.maxSizeEach = maxSizeEach; }

    public Storage getProduct() { return product; }
    public void setProduct(Storage product) { this.product = product; }

    public Storage getBoard() { return board; }
    public void setBoard(Storage board) { this.board = board; }
}