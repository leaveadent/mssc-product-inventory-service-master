package liveflow.sfg.product.inventory.service.web.mappers;

import liveflow.sfg.product.inventory.service.domain.ProductInventory;
import liveflow.sfg.factory.model.ProductInventoryDto;
import org.mapstruct.Mapper;

/**
 * Created by pg on 2019-05-31.
 */
@Mapper(uses = {DateMapper.class})
public interface ProductInventoryMapper {

    ProductInventory productInventoryDtoToProductInventory(ProductInventoryDto productInventoryDTO);

    ProductInventoryDto productInventoryToProductInventoryDto(ProductInventory productInventory);
}
