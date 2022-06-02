package liveflow.sfg.product.inventory.service.services;


import liveflow.sfg.factory.model.ProductOrderDto;

/**
 * Created by pg on 2019-09-09.
 */
public interface AllocationService {

    Boolean allocateOrder(ProductOrderDto productOrderDto);

    void deallocateOrder(ProductOrderDto productOrderDto);
}
