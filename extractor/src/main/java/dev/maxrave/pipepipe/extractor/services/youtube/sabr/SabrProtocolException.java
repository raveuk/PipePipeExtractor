package dev.maxrave.pipepipe.extractor.services.youtube.sabr;

import dev.maxrave.pipepipe.extractor.exceptions.ExtractionException;

public class SabrProtocolException extends ExtractionException {
    public SabrProtocolException(final String message) {
        super(message);
    }

    public SabrProtocolException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
