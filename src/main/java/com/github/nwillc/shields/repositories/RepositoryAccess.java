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

import com.github.nwillc.shields.XMLUtils;
import spark.Request;
import spark.Response;

import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RepositoryAccess {
    private final static Logger LOGGER = Logger.getLogger(RepositoryAccess.class.getSimpleName());
    private final static String SHIELD_URL = "https://img.shields.io/badge/%s-%s-green.svg?style=flat";

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
        String groupName = request.queryParams("group").replace('.', '/');
        String packageName = request.queryParams("package");
        LOGGER.info("Get shield for group " + groupName + " package " + packageName);
        Optional<String> latestVersion  = latestVersion(groupName, packageName);
        response.redirect(String.format(SHIELD_URL, getPath(), latestVersion.get()));
        return response;
    }

    Optional<String> getMetadatUrl(String groupName, String packageName) {
        return Optional.of(String.format(getMetadataUrlFormat(), groupName, packageName));
    }

    Optional<String> getHomepageUrl(String groupName, String packageName) {
        return Optional.of(String.format(getHomeUrlFormat(), groupName, packageName));
    }

    String getMetadataUrlFormat() {
        return metadataUrlFormat;
    }

    String getHomeUrlFormat() {
        return homeUrlFormat;
    }

    Optional<String> latestVersion(String groupName, String packageName) {
        Optional<String> metadatUrl = getMetadatUrl(groupName, packageName);
        if (!metadatUrl.isPresent()) {
            return Optional.empty();
        }
        try {
            URL url = new URL(metadatUrl.get());
            return XMLUtils.latestVersion(url.openStream());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception getting latest version: " + e);
        }
        return Optional.empty();
    }
}
