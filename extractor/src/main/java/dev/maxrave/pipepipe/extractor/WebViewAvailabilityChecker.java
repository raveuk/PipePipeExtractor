package dev.maxrave.pipepipe.extractor;

import dev.maxrave.pipepipe.extractor.exceptions.WebViewUnavailableException;

public interface WebViewAvailabilityChecker {
    void checkWebViewAvailable() throws WebViewUnavailableException;
}
