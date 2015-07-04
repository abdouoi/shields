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

import java.util.Optional;

import static com.github.nwillc.shields.repositories.RequestParams.Key.GROUP;
import static com.github.nwillc.shields.repositories.RequestParams.Key.PACKAGE;

public class GradlePlugin extends RepositoryAccess {

    private static final String HOME_URL_FORMAT = "https://plugins.gradle.org/plugin/%s.%s";

    public GradlePlugin() {
        super(null, HOME_URL_FORMAT);
    }

    @Override
    public String getPath() {
        return "gradle_plugin";
    }

    @Override
    Optional<String> lookupValue(RequestParams params) {
        return Optional.of("latest");
    }

    @Override
    String getHomepageUrl(RequestParams params) {
        params.contains(GROUP, PACKAGE);
        return String.format(getHomepageUrlFormat(), params.get(GROUP), params.get(PACKAGE));
    }

}
