//package com.sgcore.backend.config;
//
//import io.imagekit.sdk.ImageKit;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ImageKitConfig {
//
//    @Value("${imagekit.publicKey}")
//    private String publicKey;
//
//    @Value("${imagekit.privateKey}")
//    private String privateKey;
//
//    @Value("${imagekit.urlEndpoint}")
//    private String urlEndpoint;
//
//    @Bean
//    public ImageKit imageKit() {
//        ImageKit imageKit = ImageKit.getInstance();
//
//        // Fully qualified name to avoid conflict
//        io.imagekit.sdk.config.Configuration config =
//                new io.imagekit.sdk.config.Configuration(
//                        publicKey,
//                        privateKey,
//                        urlEndpoint
//                );
//
//        imageKit.setConfig(config);
//        return imageKit;
//    }
//}
package com.sgcore.backend.config;

import io.imagekit.sdk.ImageKit;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageKitConfig {

    // =========================
    // PRODUCT ACCOUNT
    // =========================

    @Value("${product.imagekit.publicKey}")
    private String productPublicKey;

    @Value("${product.imagekit.privateKey}")
    private String productPrivateKey;

    @Value("${product.imagekit.urlEndpoint}")
    private String productUrlEndpoint;


    // =========================
    // CAREER ACCOUNT
    // =========================

    @Value("${career.imagekit.publicKey}")
    private String careerPublicKey;

    @Value("${career.imagekit.privateKey}")
    private String careerPrivateKey;

    @Value("${career.imagekit.urlEndpoint}")
    private String careerUrlEndpoint;


    // =========================
    // PRODUCT IMAGEKIT
    // =========================

    @Bean(name = "productImageKit")
    public ImageKit productImageKit() {

        ImageKit imageKit =
                ImageKit.getInstance();

        io.imagekit.sdk.config.Configuration config =
                new io.imagekit.sdk.config.Configuration(
                        productPublicKey,
                        productPrivateKey,
                        productUrlEndpoint
                );

        imageKit.setConfig(config);

        return imageKit;
    }


    // =========================
    // CAREER IMAGEKIT
    // =========================

    @Bean(name = "careerImageKit")
    public ImageKit careerImageKit() {

        ImageKit imageKit =
                ImageKit.getInstance();

        io.imagekit.sdk.config.Configuration config =
                new io.imagekit.sdk.config.Configuration(
                        careerPublicKey,
                        careerPrivateKey,
                        careerUrlEndpoint
                );

        imageKit.setConfig(config);

        return imageKit;
    }
}