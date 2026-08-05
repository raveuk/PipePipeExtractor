package dev.maxrave.pipepipe.extractor.services.youtube;

import dev.maxrave.pipepipe.extractor.exceptions.ExtractionException;
import dev.maxrave.pipepipe.extractor.localization.ContentCountry;
import dev.maxrave.pipepipe.extractor.localization.Localization;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Supplies the visitor-bound token used by YouTube {@code /player} requests.
 *
 * <p>Implementations must be thread-safe. Returning {@code null} lets extraction continue without
 * a token, which is required as a compatibility fallback when the local attestation runtime is not
 * available.</p>
 */
public interface YoutubeSessionPoTokenProvider {
    @Nullable
    YoutubeSessionPoToken getSessionPoToken(@Nonnull final String clientName,
                                            @Nonnull final String clientVersion,
                                            @Nullable final String userAgent,
                                            @Nonnull final Localization localization,
                                            @Nonnull final ContentCountry contentCountry,
                                            final boolean loggedIn)
            throws IOException, ExtractionException;
}
