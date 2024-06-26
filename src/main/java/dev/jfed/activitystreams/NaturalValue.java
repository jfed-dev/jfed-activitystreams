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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Utility class to hold Natural Language values.
 *
 * @author Guillermo Castro
 * @since 0.0.1
 * @see <a href="https://www.w3.org/TR/activitystreams-core/#naturalLanguageValues">AS2#naturalLanguageValues</a>
 */
public class NaturalValue {
    public static final String UNDEFINED = "und";
    private final Locale locale;
    private final Map<Locale, String> valueMap;

    public static NaturalValueBuilder builder() {
        return new NaturalValueBuilder(Locale.ROOT);
    }

    public static class NaturalValueBuilder {
        private final NaturalValue value;

        private NaturalValueBuilder(Locale locale) {
            value = new NaturalValue(locale);
        }

        public NaturalValueBuilder withValue(String language, String value) {
            this.value.setValue(language, value);
            return this;
        }

        public NaturalValueBuilder withValue(String value) {
            this.value.setValue(value);
            return this;
        }

        public NaturalValue build() {
            return value;
        }
    }

    private NaturalValue(Locale locale) {
        this.locale = locale;
        this.valueMap = new HashMap<>();
    }

    public String getValue() {
        return getValue(locale);
    }

    public String getValue(String language) {
        return getValue(UNDEFINED.equalsIgnoreCase(language) ? Locale.ROOT : Locale.forLanguageTag(language));
    }

    public String getValue(Locale locale) {
        return valueMap.get(locale);
    }

    public void setValue(String value) {
        setValue(locale, value);
    }

    public void setValue(String language, String text) {
        setValue(UNDEFINED.equalsIgnoreCase(language) ? Locale.ROOT : Locale.forLanguageTag(language), text);
    }

    public void setValue(Locale locale, String text) {
        valueMap.put(locale, text);
    }

    public String getLanguage() {
        return Locale.ROOT.equals(locale) ? "und" : locale.getLanguage();
    }

    public boolean hasValueForLanguage(String language) {
        return valueMap.containsKey(UNDEFINED.equalsIgnoreCase(language) ? Locale.ROOT : Locale.forLanguageTag(language));
    }

    public boolean hasMultipleLanguages() {
        return valueMap.size() > 1;
    }

    public Set<Map.Entry<Locale, String>> getAllValues() {
        return valueMap.entrySet();
    }

    @Override
    public String toString() {
        return valueMap.get(locale);
    }
}