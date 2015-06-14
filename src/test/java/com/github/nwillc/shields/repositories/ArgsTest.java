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


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsTest {
    private String[] args = {"GROUP", "PACKAGE", "PATH"};

    @Test
    public void testValues() throws Exception {
        RepositoryAccess.RequestArgs.Args[] values = RepositoryAccess.RequestArgs.Args.values();
        assertThat(values).hasSize(args.length);

        for (String arg : args) {
            assertThat(RepositoryAccess.RequestArgs.Args.valueOf(arg)).isNotNull();
        }
    }
}