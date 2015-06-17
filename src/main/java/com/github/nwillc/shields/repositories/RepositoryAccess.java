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
import spark.Request;
import spark.Response;

import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RepositoryAccess {
    public static final String SHIELD_COLOR = "brightgreen";
    public static final String SHIELD_STYLE = "flat";

    private static final Logger LOGGER = Logger.getLogger(RepositoryAccess.class.getSimpleName());
    private static final String SHIELD_URL = "https://img.shields.io/badge/%s-%s-" + SHIELD_COLOR + ".svg?style=" + SHIELD_STYLE;

    private final String metadataUrlFormat;
    private final String homeUrlFormat;

    RepositoryAccess(String metadataUrlFormat, String homeUrlFormat) {
        this.metadataUrlFormat = metadataUrlFormat;
        this.homeUrlFormat = homeUrlFormat;
    }

    public String getPath() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    public Response getShield(Request request, Response response) {
        RequestArgs args = new RequestArgs(request);
        response.redirect(getShieldUrl(args));
        return response;
    }

    public Response getHomepage(Request request, Response response) {
        RequestArgs args = new RequestArgs(request);
        response.redirect(getHomepageUrl(args));
        return response;
    }

    String getMetadataUrlFormat() {
        return metadataUrlFormat;
    }

    String getMetadataUrl(RequestArgs args) {
        return String.format(getMetadataUrlFormat(), args.groupName.get(), args.packageName.get());
    }

    String getHomepageUrlFormat() {
        return homeUrlFormat;
    }

    String getHomepageUrl(RequestArgs args) {
        Optional<String> latestVersion  = lookupValue(args);
        return String.format(getHomepageUrlFormat(), args.groupName.get(), args.packageName.get(), latestVersion.get());
    }

    String getShieldUrlFormat() {
        return SHIELD_URL;
    }

    String getShieldUrl(RequestArgs args) {
        Optional<String> latestVersion  = lookupValue(args);
        return String.format(getShieldUrlFormat(), getPath(), latestVersion.get());
    }

    Optional<String> lookupValue(RequestArgs args) {
        LOGGER.info("Get latest for group " + args.groupName.get() + " package " + args.packageName.get());
        String metadatUrl = getMetadataUrl(args);
        try {
            URL url = new URL(metadatUrl);
            return XMLUtils.latestVersion(url.openStream());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception getting latest version from " + metadatUrl + ": " + e);
        }
        return Optional.empty();
    }






    // TODO: switch this to an EnumMap?
    static class RequestArgs {
        enum Args {
            GROUP,
            PACKAGE,
            PATH
        }
        final Optional<String> groupName;
        final Optional<String> packageName;
        final Optional<String> path;

        public RequestArgs(Request request) {
            groupName = Optional.ofNullable(request.queryParams(Args.GROUP.name().toLowerCase()));
            packageName = Optional.ofNullable(request.queryParams(Args.PACKAGE.name().toLowerCase()));
            path = Optional.ofNullable(request.queryParams(Args.PATH.name().toLowerCase()));
        }
    }
}
