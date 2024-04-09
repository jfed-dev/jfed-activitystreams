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

import jakarta.json.Json;
import jakarta.json.JsonValue;
import org.javatuples.Pair;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author Guillermo Castro
 * @since 0.0.1
 */
public class JsonUtils {
    private JsonUtils() {}

    public static Optional<Pair<String, JsonValue>> mapNameToJsonValue(final NaturalValue name) {
        if (name != null) {
            final Pair<String, JsonValue> response;
            if (name.hasMultipleLanguages()) {
                final var builder = Json.createObjectBuilder();
                for (Map.Entry<Locale, String> entry : name.getAllValues()) {
                    builder.add(entry.getKey().getLanguage(), entry.getValue());
                }
                response = Pair.with(ASProperties.NAME_MAP, builder.build());
            } else {
                response = Pair.with(ASProperties.NAME, Json.createValue(name.getValue()));
            }
            return Optional.of(response);
        }
        return Optional.empty();
    }

}
