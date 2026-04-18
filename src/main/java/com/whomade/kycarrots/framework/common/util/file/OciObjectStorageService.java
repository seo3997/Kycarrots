package com.whomade.kycarrots.framework.common.util.file;

import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimplePrivateKeySupplier;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.InputStream;

@Service("OciObjectStorageService")
public class OciObjectStorageService {

    @Value("${file.oci.user:}")
    private String user;

    @Value("${file.oci.fingerprint:}")
    private String fingerprint;

    @Value("${file.oci.tenancy:}")
    private String tenancy;

    @Value("${file.oci.region:}")
    private String region;

    @Value("${file.oci.key-file:}")
    private String keyFile;

    private ObjectStorageClient client;

    @PostConstruct
    public void init() throws Exception {
        if (user == null || user.isEmpty()) return;

        // OCI SDK Authentication
        AuthenticationDetailsProvider provider = SimpleAuthenticationDetailsProvider.builder()
                .tenantId(tenancy)
                .userId(user)
                .fingerprint(fingerprint)
                .privateKeySupplier(new SimplePrivateKeySupplier(keyFile))
                .build();

        client = ObjectStorageClient.builder()
                .region(com.oracle.bmc.Region.fromRegionId(region))
                .build(provider);
    }

    @PreDestroy
    public void cleanup() {
        if (client != null) {
            try {
                client.close();
            } catch (Exception e) {
                // Ignore or log
            }
        }
    }

    public void uploadFile(String namespace, String bucketName, String objectName, MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream()) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .namespaceName(namespace)
                    .bucketName(bucketName)
                    .objectName(objectName)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .putObjectBody(is)
                    .build();

            client.putObject(request);
        }
    }

    public void deleteFile(String namespace, String bucketName, String objectName) throws Exception {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .namespaceName(namespace)
                .bucketName(bucketName)
                .objectName(objectName)
                .build();

        client.deleteObject(request);
    }

    public InputStream getFile(String namespace, String bucketName, String objectName) throws Exception {
        GetObjectRequest request = GetObjectRequest.builder()
                .namespaceName(namespace)
                .bucketName(bucketName)
                .objectName(objectName)
                .build();

        return client.getObject(request).getInputStream();
    }
}
