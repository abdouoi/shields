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

import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class RepositoryAccessTest {
    private static final String METADATA_URL_FORMAT = "%s/%s";
    private static final String HOME_URL_FORMAT = "%s|%s|%s";
    private RepositoryAccess instance;
    private Request request;
    private Response response;
    private RepositoryAccess.RequestArgs args;

    @Before
    public void setUp() throws Exception {
        instance = new Dummy();
        request = mock(Request.class);
        response = mock(Response.class);
        when(request.queryParams("group")).thenReturn("group");
        when(request.queryParams("package")).thenReturn("package");
        when(request.queryParams("path")).thenReturn("path");
        args = new RepositoryAccess.RequestArgs(request);
    }

    @Test
    public void testGetMetadataUrlTemplate() throws Exception {
        assertThat(instance.getMetadataUrlFormat()).isEqualTo(METADATA_URL_FORMAT);
    }

    @Test
    public void testMetadataUrl() throws Exception {
        assertThat(instance.getMetadataUrl(args)).isEqualTo("group/package");
    }

    @Test
    public void testGetHomeUrlFormat() throws Exception {
        assertThat(instance.getHomepageUrlFormat()).isEqualTo(HOME_URL_FORMAT);
    }

    @Test
    public void testGetShieldUrl() throws Exception {
        String url = instance.getShieldUrl();
        assertThat(url).isNotNull();
        assertThat(url).contains(RepositoryAccess.SHIELD_COLOR);
        assertThat(url).contains(RepositoryAccess.SHIELD_STYLE);
    }

    @Test
    public void testGetPath() throws Exception {
        assertThat(instance.getPath()).isEqualTo("dummy");
    }

    @Test
    public void testGetShield() throws Exception {
        RepositoryAccess spy = spy(instance);
        when(spy.getShieldUrl()).thenReturn("%s|%s");
        when(spy.getPath()).thenReturn("dummy");
        doReturn(Optional.of("1")).when(spy).lookupValue(any());

        spy.getShield(request, response);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(response).redirect(argument.capture());
        assertThat(argument.getValue()).isEqualTo("dummy|1");
    }

    @Test
    public void testGetHomepage() throws Exception {
        RepositoryAccess spy = spy(instance);
        doReturn(Optional.of("1")).when(spy).lookupValue(any());

        spy.getHomepage(request, response);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(response).redirect(argument.capture());
        assertThat(argument.getValue()).isEqualTo("group|package|1");
    }

    @Test
    public void testLatestVersionArgs() throws Exception {
        RepositoryAccess spy = spy(instance);
        File file = new File("src/test/resources/maven-metadata1.xml");
        doReturn(file.toURI().toString()).when(spy).getMetadataUrl(any());

        Optional<String> stringOptional = spy.lookupValue(args);
        assertThat(stringOptional).isNotNull();
        assertThat(stringOptional.isPresent()).isTrue();
        assertThat(stringOptional.get()).isEqualTo("1.7.9");
    }

    @Test
    public void testLatestVersionArgsException() throws Exception {
        RepositoryAccess spy = spy(instance);
        doReturn(null).when(spy).getMetadataUrl(any());

        Optional<String> stringOptional = spy.lookupValue(args);
        assertThat(stringOptional).isNotNull();
        assertThat(stringOptional.isPresent()).isFalse();
    }

    private static class Dummy extends RepositoryAccess {
        public Dummy() {
            super(METADATA_URL_FORMAT, HOME_URL_FORMAT);
        }
    }
}