/*
 * Copyright (c) 2015, nwillc@gmail.com
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

import com.github.nwillc.shields.repositories.utils.XMLUtils;
import org.pmw.tinylog.Logger;
import spark.Request;
import spark.Response;

import java.net.URL;
import java.util.Optional;

import static com.github.nwillc.shields.repositories.RequestParams.Key.GROUP;
import static com.github.nwillc.shields.repositories.RequestParams.Key.PACKAGE;

public class RepositoryAccess {
    public static final String SHIELD_COLOR = "brightgreen";
    public static final String SHIELD_STYLE = "flat";

    private static final String SHIELD_URL = "http://img.shields.io/badge/%s-%s-" + SHIELD_COLOR + ".svg?style=" + SHIELD_STYLE;

    private final String metadataUrlFormat;
    private final String homeUrlFormat;

    RepositoryAccess(String metadataUrlFormat, String homeUrlFormat) {
        this.metadataUrlFormat = metadataUrlFormat;
        this.homeUrlFormat = homeUrlFormat;
    }

    public final Response getShield(Request request, Response response) throws Exception {
        RequestParams params = new RequestParams(request);
        response.redirect(getShieldUrl(params));
        return response;
    }

    public final Response getHomepage(Request request, Response response) throws Exception {
        RequestParams params = new RequestParams(request);
        response.redirect(getHomepageUrl(params));
        return response;
    }

    public String getPath() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    String getMetadataUrlFormat() {
        return metadataUrlFormat;
    }

    String getMetadataUrl(RequestParams params) {
        params.contains(GROUP, PACKAGE);
        return String.format(getMetadataUrlFormat(), params.get(GROUP).replace('.', '/'), params.get(PACKAGE));
    }

    String getHomepageUrlFormat() {
        return homeUrlFormat;
    }

    String getHomepageUrl(RequestParams params) {
        params.contains(GROUP, PACKAGE);
        Optional<String> latestVersion = lookupValue(params);
        return String.format(getHomepageUrlFormat(), params.get(GROUP), params.get(PACKAGE), latestVersion.get());
    }

    String getShieldUrlFormat() {
        return SHIELD_URL;
    }

    String getShieldUrl(RequestParams params) {
        Optional<String> latestVersion = lookupValue(params);
        return String.format(getShieldUrlFormat(), getPath(), latestVersion.get());
    }

    Optional<String> lookupValue(RequestParams params) {
        String metadatUrl = getMetadataUrl(params);
        try {
            URL url = new URL(metadatUrl);
            return XMLUtils.latestVersion(url.openStream());
        } catch (Exception e) {
            Logger.warn("Exception getting latest version from " + metadatUrl, e);
        }
        return Optional.empty();
    }

}
