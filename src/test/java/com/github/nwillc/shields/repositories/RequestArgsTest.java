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

import org.junit.Test;
import spark.Request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.github.nwillc.shields.repositories.RepositoryAccess.RequestArgs.Args.*;

public class RequestArgsTest {

    @Test
    public void testConstructor() throws Exception {
        Request request = mock(Request.class);
        when(request.queryParams(GROUP.name().toLowerCase())).thenReturn("foo");
        when(request.queryParams(PACKAGE.name().toLowerCase())).thenReturn("bar");
        when(request.queryParams(PATH.name().toLowerCase())).thenReturn("baz");

        RepositoryAccess.RequestArgs args = new RepositoryAccess.RequestArgs(request);

        assertThat(args.groupName.get()).isEqualTo("foo");
        assertThat(args.packageName.get()).isEqualTo("bar");
        assertThat(args.path.get()).isEqualTo("baz");
    }

    @Test
    public void testEmpty() throws Exception {
        Request request = mock(Request.class);
        when(request.queryParams(GROUP.name().toLowerCase())).thenReturn("foo");
        when(request.queryParams(PACKAGE.name().toLowerCase())).thenReturn("bar");
        when(request.queryParams(PATH.name().toLowerCase())).thenReturn(null);

        RepositoryAccess.RequestArgs args = new RepositoryAccess.RequestArgs(request);

        assertThat(args.groupName.get()).isEqualTo("foo");
        assertThat(args.packageName.get()).isEqualTo("bar");
        assertThat(args.path.isPresent()).isFalse();
    }
}