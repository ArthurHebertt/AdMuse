package com.admuse.infra.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PromotionRepository extends JpaRepository <PromotionEntity, UUID> {
}
