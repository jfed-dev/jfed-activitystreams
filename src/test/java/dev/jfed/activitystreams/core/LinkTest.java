/*
 * Copyright 2022-2024 Guillermo Castro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jfed.activitystreams.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import dev.jfed.activitystreams.JsonTestUtil;
import dev.jfed.activitystreams.NaturalValue;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LinkTest {
    private static final Logger log = LoggerFactory.getLogger(LinkTest.class);

    private static final String TEST_HREF = "https://example.org/abc.png";
    private static final String TEST_REL = "canonical";
    private static final String TEST_MEDIA_TYPE = "image/png";
    private static final String TEST_NAME = "Image Link";
    private static final String TEST_HREFLANG = "en";
    private static final int TEST_HEIGHT = 900;
    private static final int TEST_WIDTH = 600;

    @Test
    void testMinimal() throws Exception {
        Link link = new Link.LinkBuilder(URI.create(TEST_HREF)).build();
        String result = link.toJson();
        log.atDebug().setMessage("Test json: {}").addArgument(result).log();

        assertNotNull(result);
        JSONAssert.assertEquals("""
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "type": "Link",
                    "href": "https://example.org/abc.png"
                }
                """,
                result,
                true);
    }

    @Test
    void testAllProperties() throws Exception {
        Link link = new Link.LinkBuilder(URI.create(TEST_HREF))
            .rel(TEST_REL)
            .mediaType(TEST_MEDIA_TYPE)
            .name(NaturalValue.builder().withValue(TEST_NAME).build())
            .hreflang(TEST_HREFLANG)
            .height(TEST_HEIGHT)
            .width(TEST_WIDTH)
            .build();
        String result = link.toJson();
        log.atDebug().setMessage("Test json: {}").addArgument(result).log();

        assertNotNull(result);
        JSONAssert.assertEquals("""
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "type": "Link",
                    "href": "https://example.org/abc.png",
                    "name": "Image Link",
                    "rel": "canonical",
                    "mediaType": "image/png",
                    "hreflang": "en",
                    "height": 900,
                    "width": 600
                }
                """,
                result,
                true);
    }

    @Test
    void testVocEx2() throws Exception {
        Link testLink = getLink("test/vocabulary-ex2-jsonld.json");
        assertEquals("http://example.org/abc", testLink.getHref().toString());
        assertEquals("An example link", testLink.getName().getValue());
        assertEquals("text/html", testLink.getMediaType());
        assertEquals("en", testLink.getHreflang());

        log.atDebug().setMessage("test Link: {}").addArgument(testLink).log();
    }

    @Test
    void testVocEx136() throws Exception {
        Link testLink = getLink("test/vocabulary-ex136-jsonld.json");
        assertEquals("http://example.org/image.png", testLink.getHref().toString());
        assertEquals(100, testLink.getHeight());
        assertEquals(100, testLink.getWidth());
    }

    private Link getLink(String name) throws Exception {
        var jsonString = JsonTestUtil.getJsonFromFile(name);
        assertNotNull(jsonString);
        assertFalse(jsonString.isEmpty());

        final var result = Link.fromJson(jsonString);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        return result.get();
    }
}
