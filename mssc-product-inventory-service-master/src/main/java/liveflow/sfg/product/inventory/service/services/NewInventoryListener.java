package liveflow.sfg.product.inventory.service.services;

import liveflow.sfg.product.inventory.service.config.JmsConfig;
import liveflow.sfg.product.inventory.service.domain.ProductInventory;
import liveflow.sfg.product.inventory.service.repositories.ProductInventoryRepository;
import liveflow.sfg.factory.model.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by pg on 2019-07-21.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class NewInventoryListener {

    private final ProductInventoryRepository productInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event){

        log.debug("Got Inventory: " + event.toString());

        productInventoryRepository.save(ProductInventory.builder()
                .productId(event.getProductDto().getId())
                .upc(event.getProductDto().getUpc())
                .quantityOnHand(event.getProductDto().getQuantityOnHand())
                .build());
    }

}
