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

import spark.Request;
import spark.Response;

import java.util.Optional;

import static com.github.nwillc.shields.repositories.RequestParams.Key.PATH;

public class JCenter extends RepositoryAccess {
    private static final String METADATA_URL_FORMAT = "https://jcenter.bintray.com/%s/%s/maven-metadata.xml";
    private static final String HOME_URL_FORMAT = "https://bintray.com/%s/%s/view";

    public JCenter() {
        super(METADATA_URL_FORMAT, HOME_URL_FORMAT);
    }

    @Override
    public Response getHomepage(Request request, Response response) {
        RequestParams params = new RequestParams(request);
        params.contains(PATH);
        Optional<String> latestVersion  = lookupValue(params);
        response.redirect(String.format(getHomepageUrlFormat(), params.get(PATH), latestVersion.get()));
        return response;
    }
}
