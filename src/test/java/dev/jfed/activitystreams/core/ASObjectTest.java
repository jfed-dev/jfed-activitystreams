// Copyright 2024 Guillermo Castro
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

package dev.jfed.activitystreams.core;

import com.apicatalog.jsonld.document.JsonDocument;
import dev.jfed.activitystreams.NaturalValue;
import jakarta.json.JsonObject;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Guillermo Castro
 * @since 0.0.1
 */
class ASObjectTest {
    private static final Logger log = LoggerFactory.getLogger(ASObjectTest.class);

    private static final String TEST_ID = "https://test.example.com/object/1";
    private static final String TEST_NAME = "Test Object";

    @Test
    void testMinimal() throws JSONException {
        var object = ASObject.builder().build();
        var result = object.toJson();

        assertNotNull(result);
        JSONAssert.assertEquals("{'@context': 'https://www.w3.org/ns/activitystreams'}", result, false);
        JSONAssert.assertEquals("{'type': 'Object'}", result, false);
    }

    @Test
    void testAllProperties() throws Exception {
        var obj = ASObject.builder()
                .withId(URI.create(TEST_ID))
                .withName(NaturalValue.builder().withValue(TEST_NAME).build())
                .build();
        var result = obj.toJson();

        assertNotNull(result);
        JSONAssert.assertEquals("{'@context': 'https://www.w3.org/ns/activitystreams'}", result, false);
        JSONAssert.assertEquals("{'type': 'Object'}", result, false);
        JSONAssert.assertEquals("{'id': '" + TEST_ID + "'}", result, false);
        JSONAssert.assertEquals("{'name': '" + TEST_NAME + "'}", result, false);

        log.atInfo().setMessage("test json: {}").addArgument(result).log();
    }

    @Test
    void testFromJson() throws Exception {
        JsonDocument document = JsonDocument.of(getClass().getClassLoader().getResourceAsStream("test/core-ex8-jsonld.json"));
        assertNotNull(document);
        assertFalse(document.getJsonContent().isEmpty());
        JsonObject object = document.getJsonContent().get().asJsonObject();
        assertNotNull(object);
        final var result = ASObject.fromJsonObject(object);
        assertFalse(result.isEmpty());
        var testObject = result.get();

        assertTrue(testObject.getName().hasMultipleLanguages());
        assertEquals("This is the title", testObject.getName().getValue("en"));
        assertEquals("C'est le titre", testObject.getName().getValue("fr"));
        assertEquals("Este es el titulo", testObject.getName().getValue("sp"));

        log.atInfo().setMessage("test Object: {}").addArgument(testObject).log();
    }

}