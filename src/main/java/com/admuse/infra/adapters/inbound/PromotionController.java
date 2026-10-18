package com.admuse.infra.adapters.inbound;

import com.admuse.application.service.GeneratePromotionService;
import com.admuse.domain.model.PromotionInput;
import com.admuse.domain.model.PromotionRequest;
import com.admuse.ports.PromotionPersistencePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/promotions")
public class PromotionController {

    private final GeneratePromotionService generatePromotionService;
    private final PromotionPersistencePort promotionPersistencePort;

    public PromotionController(GeneratePromotionService generatePromotionService, PromotionPersistencePort promotionPersistencePort) {
        this.generatePromotionService = generatePromotionService;
        this.promotionPersistencePort = promotionPersistencePort;
    }

    @PostMapping ("/generate")
    public ResponseEntity<PromotionRequest> generate (@RequestBody PromotionInput input) {
        PromotionRequest request = generatePromotionService.execute(input);
        return ResponseEntity.ok(request);

    }

    @PostMapping ()
    public ResponseEntity<PromotionRequest> save (@RequestBody PromotionRequest request) {
         PromotionRequest savedRequest = promotionPersistencePort.save(request);
         return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    @GetMapping
    public ResponseEntity<List<PromotionRequest>> listAll () {
       return ResponseEntity.ok(promotionPersistencePort.findAll());

    }



}
