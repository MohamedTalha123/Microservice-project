package com.hps.user_service.handler;

import java.util.Map;

public record ErreurResponse(
        Map<String,String> errors
) {
}
