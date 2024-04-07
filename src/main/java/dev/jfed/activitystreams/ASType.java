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

import java.io.StringWriter;
import java.net.URI;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriterFactory;
import jakarta.json.stream.JsonGenerator;

/**
 * ASType is the base abstract class for all Activity Stream object types. 
 * In particular, at least Object and Link should inherit from this class since the rest of the object types derive from these two.
 * 
 * @author Guillermo Castro
 * @since 0.0.1
 */
public abstract class ASType {
    public static final String CONTEXT_VALUE = "https://www.w3.org/ns/activitystreams";
    protected URI id;
    protected NaturalValue name;
    private final JsonWriterFactory writerFactory;

    protected ASType() {
        Map<String, ?> config = Map.of(JsonGenerator.PRETTY_PRINTING, true);
        writerFactory = Json.createWriterFactory(config);
    }
    
    /** 
     * Returns the id of this object.
     * 
     * @return URI the id of the object.
     * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-id">Vocabulary#id</a>
     */
    public URI getId() {
        return id;
    }
    
    /** 
     * Sets the id of this object.
     * 
     * @param id the Object's id.
     */
    public void setId(URI id) {
        this.id = id;
    }

    /**
     * Returns the human-readable name of this object.
     * 
     * @return the name of the object.
     * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-name">Vocabulary#name</a>
     */
    public NaturalValue getName() {
        return name;
    }

    /**
     * Sets the name of the object.
     * 
     * @param name Name of the object.
     */
    public void setName(NaturalValue name) {
        this.name = name;
    }
    
    /**
     * Returns the type of the object.
     * 
     * @return Object's type.
     * @see <a href="https://www.w3.org/TR/activitystreams-vocabulary/#dfn-type">Vocabulary#type</a>
     */
    public abstract String getType();    

    /**
     * Returns the JSON representation of this object.
     * 
     * @return Object in JSON format
     */
    public String toJson() {
        return writeJsonObject(toJsonObject());
    }

    public abstract JsonObject toJsonObject();

    protected String writeJsonObject(JsonObject object) {
        var strWriter = new StringWriter();
        try (var writer = writerFactory.createWriter(strWriter)) {
            writer.writeObject(object);
        }
        return strWriter.toString();
    }
}
