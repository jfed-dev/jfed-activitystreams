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

import com.apicatalog.jsonld.lang.Keywords;
import dev.jfed.activitystreams.ASProperties;
import dev.jfed.activitystreams.ASType;
import dev.jfed.activitystreams.NaturalValue;
import jakarta.json.Json;
import jakarta.json.JsonString;
import jakarta.json.JsonStructure;
import jakarta.json.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

/**
 * @author Guillermo Castro
 * @since 0.0.1
 */
public class ASObject extends ASType {
    private static final Logger log = LoggerFactory.getLogger(ASObject.class);

    public static final String TYPE = "Object";

    private ASObject() {

    }

    @Override
    public String getType() {
        return TYPE;
    }

    public static ASObjectBuilder builder() {
        return new ASObjectBuilder();
    }

    public static class ASObjectBuilder {
        private final ASObject asObject;

        ASObjectBuilder() {
            asObject = new ASObject();
        }

        public ASObjectBuilder withName(NaturalValue name) {
            asObject.name = name;
            return this;
        }

        public ASObjectBuilder withId(URI id) {
            asObject.setId(id);
            return this;
        }

        public ASObject build() {
            return asObject;
        }
    }

    public static Optional<ASObject> fromJson(String json) {
        final var optObj = fromJsonToObject(json);
        if (optObj.isPresent()) {
            ASObjectBuilder builder = ASObject.builder();
            optObj.get().entrySet().forEach(entry -> processEntry(builder, entry));

            return Optional.of(builder.build());
        }
        return Optional.empty();
    }

    @Override
    public JsonStructure toJsonObject() {
        final var builder = Json.createObjectBuilder()
                .add(Keywords.CONTEXT, CONTEXT_VALUE);
        Optional.ofNullable(id).ifPresent(i -> builder.add(ASProperties.ID, i.toString()));
        builder.add(ASProperties.TYPE, getType());

        mapNameToJsonValue().ifPresent(objects -> builder.add(objects.getValue0(), objects.getValue1()));

        return builder.build();
    }

    @Override
    public String toString() {
        return "[ASObject: {" +
                "name=" + name + ", " +
                "}]";
    }

    private static void processEntry(final ASObjectBuilder builder, final Map.Entry<String, JsonValue> property) {
        JsonValue value = property.getValue();
        switch (property.getKey()) {
            case Keywords.CONTEXT, Keywords.TYPE, ASProperties.TYPE:
                // ignore
                break;
            case Keywords.ID, ASProperties.ID:
                builder.withId(URI.create(((JsonString)value).getString()));
                break;
            case ASProperties.NAME:
                final var name = ((JsonString)value).getString();
                builder.withName(NaturalValue.builder().withValue(name).build());
                break;
            case ASProperties.NAME_MAP:
                final var valueBuilder = NaturalValue.builder();
                property.getValue().asJsonObject()
                        .forEach((k, v) -> valueBuilder.withValue(k, ((JsonString)v).getString()));
                builder.withName(valueBuilder.build());
                break;
            default:
                log.atWarn().setMessage("Property not found: key={}, value={}").addArgument(property.getKey()).addArgument(property.getValue()).log();
        }
    }
}
