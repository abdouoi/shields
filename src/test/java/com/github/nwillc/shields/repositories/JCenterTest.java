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

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class JCenterTest {
    private RepositoryAccess instance;
    private Request request;
    private Response response;

    @Before
    public void setUp() throws Exception {
        instance = new JCenter();
        request = mock(Request.class);
        response = mock(Response.class);
        when(request.queryParams("path")).thenReturn("path");
    }

    @Test
    public void testGetPath() throws Exception {
        assertThat(instance.getPath()).isEqualTo("jcenter");
    }

    @Test
    public void testGetHomepage() throws Exception {
        RepositoryAccess spy = spy(instance);
        when(spy.getHomeUrlFormat()).thenReturn("%s|%s");
        doReturn(Optional.of("1")).when(spy).latestVersion(any());

        spy.getHomepage(request, response);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(response).redirect(argument.capture());
        assertThat(argument.getValue()).isEqualTo("path|1");
    }
}