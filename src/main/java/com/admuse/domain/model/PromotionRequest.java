package com.admuse.domain.model;

import java.time.Instant;
import java.util.UUID;

public class PromotionRequest {

    private UUID id;
    private Instant createdAt;
    private PromotionContent promotionContent;
    private PromotionInput promotionInput;

    public PromotionRequest() {
    }

    public PromotionRequest(PromotionContent promotionContent, PromotionInput promotionInput) {
        this.createdAt = Instant.now();
        this.id = UUID.randomUUID();

        if (promotionContent == null ) {throw new IllegalArgumentException("promotionContent cannot be null");
        } this.promotionContent = promotionContent;

        if (promotionInput == null) {throw new IllegalArgumentException("promotionInput cannot be null");
        } this.promotionInput = promotionInput;

    }

    public PromotionRequest(Instant createdAt, UUID id, PromotionContent promotionContent, PromotionInput promotionInput) {
        if (createdAt == null) {throw new IllegalArgumentException("createdAt cannot be null");
        } this.createdAt = createdAt;

        if (id == null) { throw new IllegalArgumentException("id cannot be null");
        }this.id = id;

        if (promotionContent == null ) {throw new IllegalArgumentException("promotionContent cannot be null");
        } this.promotionContent = promotionContent;

        if (promotionInput == null) {throw new IllegalArgumentException("promotionInput cannot be null");
        } this.promotionInput = promotionInput;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UUID getId() {
        return id;
    }

    public PromotionContent getPromotionContent() {
        return promotionContent;
    }

    public PromotionInput getPromotionInput() {
        return promotionInput;
    }

}
