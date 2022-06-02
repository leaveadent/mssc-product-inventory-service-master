package liveflow.sfg.factory.model.events;

import lombok.NoArgsConstructor;

/**
 * Created by pg on 2019-07-21.
 */
@NoArgsConstructor
public class NewInventoryEvent extends ProductEvent {
    public NewInventoryEvent(ProductDto productDto) {
        super(productDto);
    }
}
