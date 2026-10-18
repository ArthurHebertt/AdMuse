
package com.admuse.infra.adapters.outbound.persistence;

import com.admuse.domain.model.PromotionContent;
import com.admuse.domain.model.PromotionInput;
import com.admuse.domain.model.PromotionRequest;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {

    public PromotionEntity toEntity(PromotionRequest request) {
        PromotionInput input = request.getPromotionInput();
        PromotionContent content = request.getPromotionContent();

        PromotionEntity entity = new PromotionEntity();

        entity.setId(request.getId());
        entity.setCreatedAt(request.getCreatedAt());

        entity.setProductOrService(input.getProductOrService());
        entity.setTargetAudience(input.getTargetAudience());
        entity.setChannel(input.getChannel());
        entity.setStyle(input.getStyle());
        entity.setLanguage(input.getLanguage());
        entity.setObjective(input.getObjective());

        entity.setTitle(content.getTitle());
        entity.setPromotionalText(content.getPromotionalText());
        entity.setCallToAction(content.getCallToAction());
        entity.setHashtags(content.getHashtags());

        return entity;
    }


    public PromotionRequest toDomain(PromotionEntity entity) {
        PromotionInput input = new PromotionInput(
                entity.getProductOrService(),
                entity.getTargetAudience(),
                entity.getChannel(),
                entity.getStyle(),
                entity.getLanguage(),
                entity.getObjective()
        );

        PromotionContent content = new PromotionContent(
                entity.getTitle(),
                entity.getPromotionalText(),
                entity.getCallToAction(),
                entity.getHashtags()
        );

        return new PromotionRequest(
                entity.getCreatedAt(),
                entity.getId(),
                content,
                input
        );

    }


}
