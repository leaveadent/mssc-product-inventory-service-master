package liveflow.sfg.product.inventory.service.services;

import liveflow.sfg.product.inventory.service.domain.ProductInventory;
import liveflow.sfg.product.inventory.service.repositories.ProductInventoryRepository;
import liveflow.sfg.factory.model.ProductOrderDto;
import liveflow.sfg.factory.model.ProductOrderLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pg on 2019-09-09.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements AllocationService {

    private final ProductInventoryRepository productInventoryRepository;

    @Override
    public Boolean allocateOrder(ProductOrderDto productOrderDto) {
        log.debug("Allocating OrderId: " + productOrderDto.getId());

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        productOrderDto.getProductOrderLines().forEach(productOrderLine -> {
            if ((((productOrderLine.getOrderQuantity() != null ? productOrderLine.getOrderQuantity() : 0)
                    - (productOrderLine.getQuantityAllocated() != null ? productOrderLine.getQuantityAllocated() : 0)) > 0)) {
                allocateProductOrderLine(productOrderLine);
            }
            totalOrdered.set(totalOrdered.get() + productOrderLine.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (productOrderLine.getQuantityAllocated() != null ? productOrderLine.getQuantityAllocated() : 0));
        });

        log.debug("Total Ordered: " + totalOrdered.get() + " Total Allocated: " + totalAllocated.get());

        return totalOrdered.get() == totalAllocated.get();
    }

    private void allocateProductOrderLine(ProductOrderLineDto productOrderLine) {
        List<ProductInventory> productInventoryList = productInventoryRepository.findAllByUpc(productOrderLine.getUpc());

        productInventoryList.forEach(productInventory -> {
            int inventory = (productInventory.getQuantityOnHand() == null) ? 0 : productInventory.getQuantityOnHand();
            int orderQty = (productOrderLine.getOrderQuantity() == null) ? 0 : productOrderLine.getOrderQuantity();
            int allocatedQty = (productOrderLine.getQuantityAllocated() == null) ? 0 : productOrderLine.getQuantityAllocated();
            int qtyToAllocate = orderQty - allocatedQty;

            if (inventory >= qtyToAllocate) { // full allocation
                inventory = inventory - qtyToAllocate;
                productOrderLine.setQuantityAllocated(orderQty);
                productInventory.setQuantityOnHand(inventory);

                productInventoryRepository.save(productInventory);
            } else if (inventory > 0) { //partial allocation
                productOrderLine.setQuantityAllocated(allocatedQty + inventory);
                productInventory.setQuantityOnHand(0);

            }

            if (productInventory.getQuantityOnHand() == 0) {
                productInventoryRepository.delete(productInventory);
            }
        });

    }

    @Override
    public void deallocateOrder(ProductOrderDto productOrderDto) {
        productOrderDto.getProductOrderLines().forEach(productOrderLineDto -> {
            ProductInventory productInventory = ProductInventory.builder()
                    .productId(productOrderLineDto.getProductId())
                    .upc(productOrderLineDto.getUpc())
                    .quantityOnHand(productOrderLineDto.getQuantityAllocated())
                    .build();

            ProductInventory savedInventory = productInventoryRepository.save(productInventory);

            log.debug("Saved Inventory for product upc: " + savedInventory.getUpc() + " inventory id: " + savedInventory.getId());
        });
    }
}
