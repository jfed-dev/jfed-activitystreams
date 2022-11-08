// Copyright 2022 Guillermo Castro
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package dev.jfed.activitystreams.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class LinkTest {
    private static final String TEST_HREF = "http://example.org/abc.png";
    private static final String TEST_REL = "canonical";
    private static final String TEST_MEDIA_TYPE = "image/png";
    private static final String TEST_NAME = "Image Link";
    private static final String TEST_HREFLANG = "en";
    private static final int TEST_HEIGHT = 900;
    private static final int TEST_WIDTH = 600;

    @Test
    public void testMinimal() throws Exception {
        Link link = new Link.LinkBuilder(URI.create(TEST_HREF)).build();
        String result = link.toJson();

        assertNotNull(result);
        JSONAssert.assertEquals("{@context: \"https://www.w3.org/ns/activitystreams\"}", result, false);
        JSONAssert.assertEquals("{@type: \"Link\"}", result, false);
        JSONAssert.assertEquals("{href:\"" + TEST_HREF + "\"}", result, false);
    }

    @Test
    public void testAllProperties() throws Exception {
        Link link = new Link.LinkBuilder(URI.create(TEST_HREF))
            .rel(TEST_REL)
            .mediaType(TEST_MEDIA_TYPE)
            .name(TEST_NAME)
            .hreflang(TEST_HREFLANG)
            .height(TEST_HEIGHT)
            .width(TEST_WIDTH)
            .build();
        String result = link.toJson();

        assertNotNull(result);
        JSONAssert.assertEquals("{rel: \"canonical\"}", result, false);
        JSONAssert.assertEquals("{mediaType: \"" + TEST_MEDIA_TYPE + "\"}", result, false);
        JSONAssert.assertEquals("{hreflang: \"" + TEST_HREFLANG + "\"}", result, false);
        JSONAssert.assertEquals("{height: " + TEST_HEIGHT + "}", result, false);
        JSONAssert.assertEquals("{width: " + TEST_WIDTH + "}", result, false);
    }
}
