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

package com.github.nwillc.shields.repositories.utils;

import com.github.nwillc.contracts.UtilityClassContract;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class JSONUtilsTest extends UtilityClassContract {
    private static final String CODECOV_JSON = "codecov.json";
    private static final String COVERAGE = "74.56";
    private InputStream inputStream;

    @Override
    public Class<?> getClassToTest() {
        return JSONUtils.class;
    }

    @Before
    public void setUp() throws Exception {
        inputStream = ClassLoader.getSystemResourceAsStream(CODECOV_JSON);
    }

    @Test
    public void testLatestCoverage() throws Exception {
        Optional<String> stringOptional = JSONUtils.latestCodecovCoverage(inputStream);
        assertThat(stringOptional).isNotNull();
        assertThat(stringOptional.isPresent()).isTrue();
        assertThat(stringOptional.get()).isEqualTo(COVERAGE);
    }

    @Test
    public void testLatestCoverageWithException() throws Exception {
        Optional<String> stringOptional = JSONUtils.latestCodecovCoverage(null);
        assertThat(stringOptional).isNotNull();
        assertThat(stringOptional.isPresent()).isFalse();
    }
}