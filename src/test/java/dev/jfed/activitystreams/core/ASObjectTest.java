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

import dev.jfed.activitystreams.JsonTestUtil;
import dev.jfed.activitystreams.NaturalValue;
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
    private static final String TEST_NAME_ES = "Objeto de Prueba";

    @Test
    void testMinimal() throws JSONException {
        var object = ASObject.builder().build();
        var result = object.toJson();
        log.atDebug().setMessage("Test json: {}").addArgument(result).log();

        assertNotNull(result);
        JSONAssert.assertEquals("""
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "type": "Object"
                }
                """,
                result,
                true);
    }

    @Test
    void testAllProperties() throws Exception {
        var obj = ASObject.builder()
                .withId(URI.create(TEST_ID))
                .withName(NaturalValue.builder().withValue(TEST_NAME).build())
                .build();
        var result = obj.toJson();

        assertNotNull(result);
        log.atDebug().setMessage("Test json: {}").addArgument(result).log();

        JSONAssert.assertEquals("""
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "type": "Object",
                    "id": "https://test.example.com/object/1",
                    "name": "Test Object"
                }
                """,
                result,
                true);
    }

    @Test
    void testWithNameMap() throws Exception {
        var result = ASObject.builder()
                .withId(URI.create(TEST_ID))
                .withName(NaturalValue.builder()
                        .withValue("en", TEST_NAME)
                        .withValue("es", TEST_NAME_ES)
                        .build())
                .build().toJson();

        assertNotNull(result);
        log.atDebug().setMessage("Test json: {}").addArgument(result).log();

        JSONAssert.assertEquals("""
                {
                    "@context": "https://www.w3.org/ns/activitystreams",
                    "type": "Object",
                    "id": "https://test.example.com/object/1",
                    "nameMap": {
                        "es": "Objeto de Prueba",
                        "en": "Test Object"
                    }
                }
                """,
                result,
                true);
    }

    @Test
    void testNameMap() throws Exception {
        var testObject = getAsObject("test/core-ex8-jsonld.json");

        assertTrue(testObject.getName().hasMultipleLanguages());
        assertEquals("This is the title", testObject.getName().getValue("en"));
        assertEquals("C'est le titre", testObject.getName().getValue("fr"));
        assertEquals("Este es el titulo", testObject.getName().getValue("sp"));

        log.atDebug().setMessage("test Object: {}").addArgument(testObject).log();
    }

    @Test
    void testUndefinedNameMap() throws Exception {
        var testObject = getAsObject("test/core-ex11b-jsonld.json");

        assertNotNull(testObject);
        assertFalse(testObject.getName().hasMultipleLanguages());
        assertEquals("This is the title", testObject.getName().getValue(NaturalValue.UNDEFINED));
        assertEquals("This is the title", testObject.getName().getValue());
    }

    @Test
    void testLanguageInContext() throws Exception {
        var testObject = getAsObject("test/core-ex11c-jsonld.json");

        assertNotNull(testObject);
        assertFalse(testObject.getName().hasMultipleLanguages());
        assertEquals("This is the title", testObject.getName().getValue("en"));
    }

    @Test
    void testVocabEx1() throws Exception {
        var testObject = getAsObject("test/vocabulary-ex1-jsonld.json");

        assertNotNull(testObject);
        assertEquals("http://www.test.example/object/1", testObject.getId().toString());
        assertEquals("A Simple, non-specific object", testObject.getName().getValue());
    }

    private ASObject getAsObject(String name) throws Exception {
        var jsonString = JsonTestUtil.getJsonFromFile(name);
        assertNotNull(jsonString);
        assertFalse(jsonString.isEmpty());

        var result = ASObject.fromJson(jsonString);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        return result.get();
    }
}