package com.hps.paymentservice.http;



import com.hps.paymentservice.dto.PurchaseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(
        name = "product-service",
        url = "${application.config.product-url}"
)
public interface ProductClient {

    @PostMapping("/update")
    Boolean updateProductsQuantity(@RequestBody List<PurchaseRequest> purchaseRequest);


}

