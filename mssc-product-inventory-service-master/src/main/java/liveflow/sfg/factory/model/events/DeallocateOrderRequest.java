package liveflow.sfg.factory.model.events;

import liveflow.sfg.factory.model.ProductOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by pg on 2/29/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeallocateOrderRequest {
    private ProductOrderDto productOrderDto;
}
