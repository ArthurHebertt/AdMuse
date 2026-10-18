package com.admuse.domain.model;


public class PromotionInput {

    private String productOrService;
    private String targetAudience;
    private String channel;
    private String style;
    private String language;
    private String objective;


    public String validateRequiredField (String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException( field + " Cannot be blank");
        } return value.trim();
    }

    public PromotionInput(String productOrService, String targetAudience, String channel, String style, String language, String objective) {
        this.productOrService = validateRequiredField(productOrService, "productOrService");
        this.targetAudience = validateRequiredField(targetAudience, "targetAudience");
        this.channel = validateRequiredField(channel, "channel");
        this.style = validateRequiredField(style, "style");
        this.language = validateRequiredField(language, "language");
        this.objective = validateRequiredField(objective, "objective");
    }

    public String getProductOrService() {
        return productOrService;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public String getChannel() {
        return channel;
    }

    public String getStyle() {
        return style;
    }

    public String getLanguage() {
        return language;
    }

    public String getObjective() {
        return objective;
    }
}
