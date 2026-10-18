
package com.admuse.application.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class WatermarkService {

    private static final float LOGO_OPACITY = 0.75f;

    private static final int MIN_FONT_SIZE = 36;
    private static final int MIN_MARGIN = 20;
    private static final int MIN_PADDING = 12;

    public BufferedImage loadImage(InputStream inputStream)
            throws IOException {

        BufferedImage image = ImageIO.read(inputStream);

        if (image == null) {
            throw new IOException(
                    "Unsupported or invalid image."
            );
        }

        return image;
    }

    public BufferedImage addTextWatermark(
            BufferedImage image,
            String watermarkText
    ) {
        if (watermarkText == null || watermarkText.isBlank()) {
            throw new IllegalArgumentException(
                    "Watermark text must not be blank."
            );
        }

        Graphics2D graphics = image.createGraphics();

        try {
            graphics.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );

            graphics.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            graphics.setRenderingHint(
                    RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON
            );

            int fontSize = Math.max(
                    MIN_FONT_SIZE,
                    image.getWidth() / 18
            );

            graphics.setFont(
                    new Font("Arial", Font.BOLD, fontSize)
            );

            FontMetrics metrics = graphics.getFontMetrics();

            int margin = Math.max(
                    MIN_MARGIN,
                    image.getWidth() / 40
            );

            int padding = Math.max(
                    MIN_PADDING,
                    fontSize / 3
            );

            int maxTextWidth = image.getWidth()
                    - (margin * 2)
                    - (padding * 2);

            if (maxTextWidth <= 0) {
                return image;
            }

            String text = fitText(
                    watermarkText.trim(),
                    metrics,
                    maxTextWidth
            );

            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getHeight();

            int boxWidth = textWidth + (padding * 2);
            int boxHeight = textHeight + (padding * 2);

            // Center horizontally and move slightly upward.
            int x = (image.getWidth() - boxWidth) / 2;
            int y = image.getHeight()
                    - margin
                    - boxHeight
                    - (image.getHeight() / 10);

            // Keep the watermark inside the image.
            x = Math.max(0, Math.min(x, image.getWidth() - boxWidth));
            y = Math.max(0, Math.min(y, image.getHeight() - boxHeight));

            // Translucent dark rounded background.
            graphics.setColor(new Color(0, 0, 0, 165));

            graphics.fillRoundRect(
                    x,
                    y,
                    boxWidth,
                    boxHeight,
                    24,
                    24
            );

            int textX = x + padding;
            int textY = y + padding + metrics.getAscent();

            // Subtle text shadow.
            graphics.setColor(new Color(0, 0, 0, 200));
            graphics.drawString(text, textX + 2, textY + 2);

            // Main watermark text.
            graphics.setColor(Color.WHITE);
            graphics.drawString(text, textX, textY);

        } finally {
            graphics.dispose();
        }

        return image;
    }

    private String fitText(
            String text,
            FontMetrics metrics,
            int maxWidth
    ) {
        if (metrics.stringWidth(text) <= maxWidth) {
            return text;
        }

        String suffix = "...";
        int availableWidth = maxWidth - metrics.stringWidth(suffix);

        if (availableWidth <= 0) {
            return suffix;
        }

        int end = text.length();

        while (end > 0
                && metrics.stringWidth(text.substring(0, end))
                > availableWidth) {
            end--;
        }

        return text.substring(0, end).trim() + suffix;
    }

    public BufferedImage addLogoWatermark(
            BufferedImage image,
            BufferedImage logo
    ) {
        if (image.getWidth() < 4 || image.getHeight() < 4) {
            throw new IllegalArgumentException(
                    "Base image is too small."
            );
        }

        if (logo == null
                || logo.getWidth() <= 0
                || logo.getHeight() <= 0) {
            throw new IllegalArgumentException(
                    "Logo image is invalid."
            );
        }

        int margin = Math.max(
                MIN_MARGIN,
                image.getWidth() / 40
        );

        int maxWidth = Math.max(1, image.getWidth() / 4);
        int maxHeight = Math.max(1, image.getHeight() / 4);

        double scale = Math.min(
                (double) maxWidth / logo.getWidth(),
                (double) maxHeight / logo.getHeight()
        );

        int logoWidth = Math.max(
                1,
                (int) (logo.getWidth() * scale)
        );

        int logoHeight = Math.max(
                1,
                (int) (logo.getHeight() * scale)
        );

        int x = Math.max(
                0,
                image.getWidth() - logoWidth - margin
        );

        int y = Math.max(
                0,
                image.getHeight() - logoHeight - margin
        );

        Graphics2D graphics = image.createGraphics();

        try {
            graphics.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC
            );

            graphics.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            graphics.setComposite(
                    AlphaComposite.getInstance(
                            AlphaComposite.SRC_OVER,
                            LOGO_OPACITY
                    )
            );

            graphics.drawImage(
                    logo,
                    x,
                    y,
                    logoWidth,
                    logoHeight,
                    null
            );

        } finally {
            graphics.dispose();
        }

        return image;
    }

    public byte[] exportAsPng(BufferedImage image)
            throws IOException {

        try (ByteArrayOutputStream output =
                     new ByteArrayOutputStream()) {

            boolean written = ImageIO.write(
                    image,
                    "png",
                    output
            );

            if (!written) {
                throw new IOException(
                        "Could not export image as PNG."
                );
            }

            return output.toByteArray();
        }
    }

    public byte[] processTextWatermark(
            InputStream inputStream,
            String watermarkText
    ) throws IOException {

        BufferedImage image = loadImage(inputStream);

        addTextWatermark(image, watermarkText);

        return exportAsPng(image);
    }

    public byte[] processLogoWatermark(
            InputStream imageInputStream,
            InputStream logoInputStream
    ) throws IOException {

        BufferedImage image = loadImage(imageInputStream);
        BufferedImage logo = loadImage(logoInputStream);

        addLogoWatermark(image, logo);

        return exportAsPng(image);
    }
}
