package com.admuse.infra.adapters.outbound.persistence;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Promotions")
public class PromotionEntity {

    @Id

    private UUID id;

    private Instant createdAt;

    // PromotionInput
    private String productOrService;
    private String targetAudience;
    private String channel;
    private String style;
    private String language;
    private String objective;

    // PromotionContent
    private String title;

    @Column(columnDefinition = "TEXT")
    private String promotionalText;

    private String callToAction;

    @ElementCollection
    @CollectionTable(
            name = "promotion_hashtags",
            joinColumns = @JoinColumn(name = "promotion_id")
    )
    @Column(name = "hashtag")
    private List<String> hashtags = new ArrayList<>();

    public PromotionEntity() {
    }

    public PromotionEntity(String callToAction, String channel, Instant createdAt, List<String> hashtags, UUID id, String language, String objective, String productOrService, String promotionalText, String style, String targetAudience, String title) {
        this.callToAction = callToAction;
        this.channel = channel;
        this.createdAt = createdAt;
        this.hashtags = hashtags;
        this.id = id;
        this.language = language;
        this.objective = objective;
        this.productOrService = productOrService;
        this.promotionalText = promotionalText;
        this.style = style;
        this.targetAudience = targetAudience;
        this.title = title;
    }

    public String getCallToAction() {
        return callToAction;
    }

    public String getChannel() {
        return channel;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public UUID getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getObjective() {
        return objective;
    }

    public String getProductOrService() {
        return productOrService;
    }

    public String getPromotionalText() {
        return promotionalText;
    }

    public String getStyle() {
        return style;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public String getTitle() {
        return title;
    }

    public void setCallToAction(String callToAction) {
        this.callToAction = callToAction;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public void setProductOrService(String productOrService) {
        this.productOrService = productOrService;
    }

    public void setPromotionalText(String promotionalText) {
        this.promotionalText = promotionalText;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
