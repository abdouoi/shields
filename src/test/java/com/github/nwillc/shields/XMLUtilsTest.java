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

package com.github.nwillc.shields;

import com.github.nwillc.contracts.UtilityClassContract;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class XMLUtilsTest extends UtilityClassContract {
    private static final String LATEST_1 = "1.7.9";
    private static final String MAVEN_METADATA1_XML = "maven-metadata1.xml";
    private InputStream metadata1;

    @Before
    public void setUp() throws Exception {
        metadata1 = ClassLoader.getSystemResourceAsStream(MAVEN_METADATA1_XML);
        assertThat(metadata1).isNotNull();
    }

    @Override
    public Class<?> getClassToTest() {
        return XMLUtils.class;
    }

    @Test
    public void testLatest1() throws Exception {
        Optional<String> latest = XMLUtils.latestVersion(metadata1);
        assertThat(latest.isPresent()).isTrue();
        assertThat(latest.get()).isEqualTo(LATEST_1);
    }

    @Test
    public void testExceptionCase() throws Exception {
        Optional<String> latest = XMLUtils.latestVersion(null);
        assertThat(latest.isPresent()).isFalse();
    }
}