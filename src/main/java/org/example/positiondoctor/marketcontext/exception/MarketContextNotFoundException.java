package org.example.positiondoctor.marketcontext.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MarketContextNotFoundException extends RuntimeException {

    public MarketContextNotFoundException() {
        super("Market context report not found");
    }
}
