package com.admuse.domain.model;

import java.util.List;

public class PromotionContent {

    private String title;
    private String promotionalText;
    private String callToAction;
    private List<String> hashtags;

    public String validateRequiredField(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " Cannot be blank");
        }
        return value.trim();
    }


    public PromotionContent(String title, String promotionalText, String callToAction, List<String> hashtags) {
        this.title = validateRequiredField(title, "title");
        this.promotionalText = validateRequiredField(promotionalText, "promotionalText");
        this.callToAction = validateRequiredField(callToAction, "callToAction");
        if (hashtags == null) {
            this.hashtags = List.of();
        } else {
            this.hashtags = List.copyOf(hashtags);
        }
    }


    public String getTitle() {
        return title;
    }

    public String getPromotionalText() {
        return promotionalText;
    }

    public String getCallToAction() {
        return callToAction;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

}