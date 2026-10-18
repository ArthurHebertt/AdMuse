package com.admuse.infra.adapters.outbound.persistence;

import com.admuse.domain.model.PromotionRequest;
import com.admuse.ports.PromotionPersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionAdapter implements PromotionPersistencePort {

    private final PromotionMapper promotionMapper;
    private final PromotionRepository promotionRepository;

    public PromotionAdapter(PromotionMapper promotionMapper, PromotionRepository promotionRepository) {
        this.promotionMapper = promotionMapper;
        this.promotionRepository = promotionRepository;
    }

    @Override
    public PromotionRequest save (PromotionRequest promotionRequest) {
        PromotionEntity entity = promotionMapper.toEntity(promotionRequest);
        PromotionEntity savedEntity = promotionRepository.save(entity);

        return promotionMapper.toDomain(savedEntity);
    }

    @Override
    public List<PromotionRequest> findAll () {
        List<PromotionEntity> list = promotionRepository.findAll();

        return list.stream()
                .map(promotionMapper::toDomain)
                .toList();
    }



}
