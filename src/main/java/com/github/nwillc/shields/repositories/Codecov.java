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

import static com.github.nwillc.shields.repositories.RequestParams.Key.PACKAGE;
import static com.github.nwillc.shields.repositories.RequestParams.Key.PATH;

public class Codecov extends RepositoryAccess {
    public static final String HOMEPAGE_URL_FORMAT = "https://codecov.io/%s/%s?branch=master";
    public static final String SHIELD_URL_FORMAT = "http://codecov.io/%s/%s/coverage.svg?branch=master";

    public Codecov() {
        super(null, HOMEPAGE_URL_FORMAT);
    }

    @Override
    String getHomepageUrl(RequestParams params) {
        params.contains(PATH, PACKAGE);
        return String.format(getHomepageUrlFormat(), params.get(PATH), params.get(PACKAGE));
    }

    @Override
    String getShieldUrlFormat() {
        return SHIELD_URL_FORMAT;
    }

    @Override
    String getShieldUrl(RequestParams params) {
        params.contains(PATH, PACKAGE);
        return String.format(getShieldUrlFormat(), params.get(PATH), params.get(PACKAGE));
    }
}
