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

package com.github.nwillc.shields;

import com.github.nwillc.shields.repositories.RequestParams;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Runtime exception thrown when an expected parameter of a request is missing.
 */
public class MissingParamException extends RuntimeException {
    private final RequestParams.Key[] keys;

    public MissingParamException(RequestParams.Key ... keys) {
        this.keys = keys;
    }

    @Override
    public String getMessage() {
        return String.format("Missing on of the required parameters: %s\n",
                Stream.of(keys).map(k -> k.name().toLowerCase()).collect(Collectors.joining(", ")));
    }
}
