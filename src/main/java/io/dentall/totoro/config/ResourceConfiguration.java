package io.dentall.totoro.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {

    public static final String IMAGE_FILE_BASE = "./build/images/";
    public static final String IMAGE_URL_BASE = "/images/";

    private final Logger log = LoggerFactory.getLogger(ResourceConfiguration.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path imageDir = Paths.get(IMAGE_FILE_BASE);
        if (!Files.isDirectory(imageDir)) {
            try {
                Files.createDirectory(imageDir);
                log.info("Created images directory");
            } catch(IOException e) {
                log.error("Failed to create images directory: {}", e);
            }
        }

        registry.addResourceHandler(IMAGE_URL_BASE + "**").addResourceLocations("file:" + IMAGE_FILE_BASE);
    }
}
