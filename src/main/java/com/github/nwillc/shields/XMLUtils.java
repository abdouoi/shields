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

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Logger;

public final class XMLUtils {
    private final static Logger LOGGER = Logger.getLogger(XMLUtils.class.getSimpleName());
    private static final String LATEST = "latest";

    private XMLUtils() {}

    /**
     * Given the XML of a maven-metadata.xml file extract the value of the <code>latest</code> element if present.
     * @param metadata1 input stream to maven-metadata.xml
     * @return optional value of latest element.
     */
    public static Optional<String> latestVersion(InputStream metadata1) {
        try {
            final XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(metadata1);
            xmlStreamReader.getEventType();
            while (xmlStreamReader.hasNext()) {
                final int next = xmlStreamReader.next();
                if (next == XMLStreamConstants.START_ELEMENT && xmlStreamReader.getLocalName().equalsIgnoreCase(LATEST)) {
                    return Optional.of(xmlStreamReader.getElementText());
                }
            }
        } catch (XMLStreamException e) {
            LOGGER.warning("Unable to parse maven-metadata.xml stream: " + e);
        }
        return Optional.empty();
    }
}
