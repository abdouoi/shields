/*
 * CCopyright (c) 2015, nwillc@gmail.com
 *
 *  Permission to use, copy, modify, and/or distribute this software for any
 *  purpose with or without fee is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

package com.github.nwillc.shields.repositories;

import com.github.nwillc.shields.repositories.utils.JSONUtils;
import com.github.nwillc.shields.repositories.utils.XMLUtils;

import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Codecov extends RepositoryAccess {
    private static final Logger LOGGER = Logger.getLogger(Codecov.class.getSimpleName());
    public static final String HOMEPAGE_URL_FORMAT = "https://codecov.io/%s/%s";
    public static final String METADATA_URL_FORMAT = HOMEPAGE_URL_FORMAT + "?access_token=%s";

    public Codecov() {
        super(METADATA_URL_FORMAT, HOMEPAGE_URL_FORMAT);
    }

    @Override
    String getHomepageUrl(RequestArgs args) {
        return String.format(getHomeUrlFormat(), args.path.get(), args.packageName.get());
    }

    @Override
    String getMetadatUrl(RequestArgs args) {
        return String.format(getMetadataUrlFormat(), args.path.get(), args.packageName.get(), args.token.get());
    }

    @Override
    Optional<String> latestVersion(RequestArgs args) {
        String metadatUrl = getMetadatUrl(args);
        try {
            URL url = new URL(metadatUrl);
            return JSONUtils.latestCodecovCoverage(url.openStream());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception getting latest version from " + metadatUrl + ": " + e);
        }
        return Optional.empty();
    }
}
