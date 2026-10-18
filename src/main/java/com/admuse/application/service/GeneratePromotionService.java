package com.admuse.application.service;

import com.admuse.domain.model.PromotionContent;
import com.admuse.domain.model.PromotionInput;
import com.admuse.domain.model.PromotionRequest;
import com.admuse.ports.ContentGenerator;
import org.springframework.stereotype.Service;

@Service
public class GeneratePromotionService {

    private final ContentGenerator contentGenerator;

    public GeneratePromotionService(ContentGenerator contentGenerator) {
        this.contentGenerator = contentGenerator;
    }

    public PromotionRequest execute (PromotionInput input) {

        PromotionContent content = contentGenerator.generate(input);

        PromotionRequest request = new PromotionRequest(content, input);

        return request;



    }

}
