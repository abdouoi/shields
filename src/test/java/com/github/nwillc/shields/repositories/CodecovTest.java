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

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import spark.Request;
import spark.Response;

import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class CodecovTest {
    private RepositoryAccess instance;
    private Request request;
    private Response response;
    private RepositoryAccess.RequestArgs args;

    @Before
    public void setUp() throws Exception {
        instance = new Codecov();
        request = mock(Request.class);
        response = mock(Response.class);
        when(request.queryParams("package")).thenReturn("package");
        when(request.queryParams("path")).thenReturn("path");
        when(request.queryParams("token")).thenReturn("token");
        args = new RepositoryAccess.RequestArgs(request);
    }

    @Test
    public void testPath() throws Exception {
        assertThat(instance.getPath()).isEqualTo("codecov");
    }

    @Test
    public void testGetMetadataUrl() throws Exception {
        RepositoryAccess spy = spy(instance);
        when(spy.getMetadataUrlFormat()).thenReturn("%s|%s|%s");
        assertThat(spy.getMetadataUrl(args)).isEqualTo("path|package|token");
    }

    @Test
    public void testGetHomepageUrl() throws Exception {
        RepositoryAccess spy = spy(instance);
        when(spy.getHomepageUrlFormat()).thenReturn("%s|%s");
        assertThat(spy.getHomepageUrl(args)).isEqualTo("path|package");
    }

    @Test
    public void testGetHomepage() throws Exception {
        RepositoryAccess spy = spy(instance);
        when(spy.getHomepageUrlFormat()).thenReturn("%s|%s");

        spy.getHomepage(request, response);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(response).redirect(argument.capture());
        assertThat(argument.getValue()).isEqualTo("path|package");
    }

    @Test
    public void testLookupValue() throws Exception {
        RepositoryAccess spy = spy(instance);
        File file = new File("src/test/resources/codecov.json");
        doReturn(file.toURI().toString()).when(spy).getMetadataUrl(any());

        Optional<String> stringOptional = spy.lookupValue(args);
        assertThat(stringOptional).isNotNull();
        assertThat(stringOptional.isPresent()).isTrue();
        assertThat(stringOptional.get()).isEqualTo("74.56");
    }

    @Test
    public void testLookupValueException() throws Exception {
        RepositoryAccess spy = spy(instance);
        doReturn(null).when(spy).getMetadataUrl(any());

        Optional<String> stringOptional = spy.lookupValue(args);
        assertThat(stringOptional).isNotNull();
        assertThat(stringOptional.isPresent()).isFalse();
    }
}