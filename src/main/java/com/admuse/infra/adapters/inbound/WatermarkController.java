
package com.admuse.infra.adapters.inbound;

import com.admuse.application.service.WatermarkService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/watermarks")
public class WatermarkController {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private final WatermarkService watermarkService;

    public WatermarkController(WatermarkService watermarkService) {
        this.watermarkService = watermarkService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> applyWatermark(
            @RequestParam("image") MultipartFile image,
            @RequestParam("type") String type,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "logo", required = false) MultipartFile logo
    ) {

        validateImageFile(image);

        try {
            byte[] result;

            switch (type.trim().toUpperCase()) {
                case "TEXT" -> {
                    if (text == null || text.isBlank()) {
                        throw new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Watermark text is required."
                        );
                    }

                    if (text.length() > 200) {
                        throw new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Watermark text must not exceed 200 characters."
                        );
                    }

                    try (var input = image.getInputStream()) {
                        result = watermarkService.processTextWatermark(
                                input, text.trim()
                        );
                    }
                }

                case "LOGO" -> {
                    validateImageFile(logo);

                    try (var input = image.getInputStream();
                         var logoInput = logo.getInputStream()) {

                        result = watermarkService.processLogoWatermark(
                                input, logoInput
                        );
                    }
                }

                default -> throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Type must be TEXT or LOGO."
                );
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"watermarked.png\""
                    )
                    .body(result);

        } catch (IOException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not process the uploaded image. Use a valid PNG or JPEG file.",
                    exception
            );
        }
    }

    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "An image file is required."
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ResponseStatusException(
                    HttpStatus.PAYLOAD_TOO_LARGE,
                    "Each image must be 5 MB or smaller."
            );
        }

        String contentType = file.getContentType();

        if (!MediaType.IMAGE_PNG_VALUE.equals(contentType)
                && !MediaType.IMAGE_JPEG_VALUE.equals(contentType)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only PNG and JPEG images are accepted."
            );
        }
    }
}
