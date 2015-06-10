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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class RepositoryAccessTest {
    private static final String METADATA_URL_FORMAT = "%s/%s";
    private static final String HOME_URL_FORMAT = "%s|%s|%s";
    private RepositoryAccess instance;

    @Before
    public void setUp() throws Exception {
        instance = new Dummy();
    }

    @Test
    public void testGetMetadataUrlTemplate() throws Exception {
        assertThat(instance.getMetadataUrlFormat()).isEqualTo(METADATA_URL_FORMAT);
    }

    @Test
    public void testGetHomeUrlFormat() throws Exception {
        assertThat(instance.getHomeUrlFormat()).isEqualTo(HOME_URL_FORMAT);
    }

    @Test
    public void testGetHomeUrl() throws Exception {
        Optional<String> url = instance.getHomepageUrl("foo", "bar", "1");
        assertThat(url).isNotNull();
        assertThat(url.isPresent()).isTrue();
        assertThat(url.get()).isEqualTo("foo|bar|1");

    }

    @Test
    public void testMetadataUrl() throws Exception {
        Optional<String> url = instance.getMetadatUrl("foo", "bar");
        assertThat(url).isNotNull();
        assertThat(url.isPresent()).isTrue();
        assertThat(url.get()).isEqualTo("foo/bar");
    }

    @Test
    public void testGetPath() throws Exception {
        assertThat(instance.getPath()).isEqualTo("dummy");
    }

    private static class Dummy extends RepositoryAccess {
        public Dummy() {
            super(METADATA_URL_FORMAT, HOME_URL_FORMAT);
        }

        @Override
        public Optional<String> getMetadatUrl(String groupName, String packageName) {
            return Optional.of(String.format(METADATA_URL_FORMAT, groupName, packageName));
        }

        @Override
        public Optional<String> getHomepageUrl(String groupName, String packageName, String version) {
            return Optional.of(String.format(HOME_URL_FORMAT, groupName, packageName, version));
        }
    }
}