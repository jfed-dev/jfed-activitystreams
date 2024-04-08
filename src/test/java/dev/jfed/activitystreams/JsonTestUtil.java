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

package dev.jfed.activitystreams;

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.http.media.MediaType;
import jakarta.json.JsonObject;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Guillermo Castro
 * @since 0.0.1
 */
public class JsonTestUtil {
    private JsonTestUtil() {}

    public static JsonObject getJsonObject(String name) throws JsonLdError {
        var document = JsonDocument.of(MediaType.of("application", "activity+json"), JsonTestUtil.class.getClassLoader().getResourceAsStream(name));
        assertNotNull(document);
        assertFalse(document.getJsonContent().isEmpty());
        var object = JsonLd.compact(document, URI.create(ASType.CONTEXT_VALUE)).get();
        assertNotNull(object);
        return object;
    }

}
