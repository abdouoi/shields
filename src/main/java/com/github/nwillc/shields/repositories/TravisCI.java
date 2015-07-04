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


public class TravisCI extends Codecov {
    private static final String SHIELD_URL_FORMAT = "https://travis-ci.org/%s/%s.svg?branch=master";
    private static final String HOMEPAGE_URL_FORMAT = "https://travis-ci.org/%s/%s";

    public TravisCI() {
        super(null, HOMEPAGE_URL_FORMAT);
    }

    @Override
    public String getPath() {
        return "travis-ci";
    }

    @Override
    String getShieldUrlFormat() {
        return SHIELD_URL_FORMAT;
    }
}
