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

import com.github.nwillc.shields.MissingParamException;
import spark.Request;

import java.util.EnumMap;
import java.util.stream.Stream;

/**
 * Support for the query params supported by this service.
 */
public class RequestParams {

    /**
     * An enumeration of the parameters supported.
     */
    public enum Key {
        GROUP,
        PACKAGE,
        PATH
    }

    final private EnumMap<Key, String> params = new EnumMap<>(Key.class);

    /**
     * Create and instance of Request params based on a request. Extracts the available supported parameters.
     *
     * @param request to pull params from
     */
    public RequestParams(Request request) {
        for (Key key : Key.values()) {
            final String value = request.queryParams(key.name().toLowerCase());
            if (value != null) {
                params.put(key, value);
            }
        }
    }

    /**
     * Get the value of specified key.
     *
     * @param key the parameter to get
     * @return the parameter value, or null if not present
     */
    public String get(Key key) {
        return params.get(key);
    }

    /**
     * Assert that this instance contains a specified set of parameters.
     *
     * @param keys the parameters that must be present
     * @throws MissingParamException if any of the parameters are missing
     */
    public void contains(Key... keys) throws MissingParamException {
        if (!Stream.of(keys).allMatch(params::containsKey)) {
            throw new MissingParamException(keys);
        }
    }
}
