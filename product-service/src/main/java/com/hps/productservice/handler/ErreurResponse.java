package com.hps.productservice.handler;

import java.util.Map;

public record ErreurResponse(
        Map<String,String> errors
) {
}
