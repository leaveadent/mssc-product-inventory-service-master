package liveflow.sfg.factory.model.events;

import liveflow.sfg.factory.model.ProductOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by pg on 12/3/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderResult {
    private ProductOrderDto productOrderDto;
    private Boolean allocationError = false;
    private Boolean pendingInventory = false;
}
