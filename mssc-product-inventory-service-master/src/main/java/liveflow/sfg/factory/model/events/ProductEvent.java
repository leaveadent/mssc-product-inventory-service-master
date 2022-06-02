package liveflow.sfg.factory.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by pg on 2019-07-21.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductEvent implements Serializable {

    static final long serialVersionUID = -5781515597148163111L;

    private ProductDto productDto;
}
