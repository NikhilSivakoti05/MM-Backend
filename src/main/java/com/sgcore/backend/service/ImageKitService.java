//package com.sgcore.backend.service;
//
//import io.imagekit.sdk.ImageKit;
//import io.imagekit.sdk.models.FileCreateRequest;
//import io.imagekit.sdk.models.results.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class ImageKitService {
//
//    @Autowired
//    private ImageKit imageKit;
//
//    public String uploadImage(MultipartFile file) throws Exception {
//        FileCreateRequest request = new FileCreateRequest(
//                file.getBytes(),
//                file.getOriginalFilename()
//        );
//        request.setFolder("/products");
//        Result result = imageKit.upload(request);
//        return result.getUrl();
//    }
//}
