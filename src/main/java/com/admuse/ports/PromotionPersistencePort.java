package com.admuse.ports;

import com.admuse.domain.model.PromotionRequest;

import java.util.List;

public interface PromotionPersistencePort {

    PromotionRequest save (PromotionRequest promotionRequest);

    List<PromotionRequest> findAll ();

}
