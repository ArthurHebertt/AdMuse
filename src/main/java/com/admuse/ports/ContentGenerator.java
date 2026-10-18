package com.admuse.ports;

import com.admuse.domain.model.PromotionContent;
import com.admuse.domain.model.PromotionInput;

public interface ContentGenerator {
    PromotionContent generate (PromotionInput input);


}