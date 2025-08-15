package com.ecommerce.project.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    @Autowired
    private Cloudinary cloudinary;

    public String upload(MultipartFile file) throws IOException {
        Map options = ObjectUtils.asMap(
                "folder", "products",
                "resource_type", "auto"
        );

        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);
        String secureUrl = result.get("secure_url").toString();

        logger.debug("✅ Uploaded to Cloudinary: {}", secureUrl);
        return secureUrl;
    }


}
