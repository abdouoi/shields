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
import org.junit.Test;
import spark.Request;

import static com.github.nwillc.shields.repositories.RequestParams.Key.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestParamsTest {

    @Test
    public void testConstructor() throws Exception {
        Request request = mock(Request.class);
        when(request.queryParams(GROUP.name().toLowerCase())).thenReturn("foo");
        when(request.queryParams(PACKAGE.name().toLowerCase())).thenReturn("bar");
        when(request.queryParams(PATH.name().toLowerCase())).thenReturn("baz");

        RequestParams params = new RequestParams(request);

        assertThat(params.get(GROUP)).isEqualTo("foo");
        assertThat(params.get(PACKAGE)).isEqualTo("bar");
        assertThat(params.get(PATH)).isEqualTo("baz");
    }

    @Test
    public void testMissingParameter() throws Exception {
        Request request = mock(Request.class);
        RequestParams params = new RequestParams(request);
        try {
            params.contains(PATH);
            failBecauseExceptionWasNotThrown(MissingParamException.class);
        } catch (MissingParamException p) {
            assertThat(p.getMessage()).contains("path");
        }
    }
}