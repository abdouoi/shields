package com.github.nwillc.shields.repositories.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class AutoCloseableXMLStreamReaderTest {

    @Test
    public void testNullClose() throws Exception {
        AutoCloseableXMLStreamReader xmlStreamReader = new AutoCloseableXMLStreamReader(null);

        assertNotNull(xmlStreamReader);
        xmlStreamReader.close();
    }
}