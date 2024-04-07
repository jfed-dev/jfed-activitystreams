// Copyright 2022-2024 Guillermo Castro
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
package dev.jfed.activitystreams;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Utility class to hold Natural Language values.
 *
 * @author Guillermo Castro
 * @since 0.0.1
 * @see <a href="https://www.w3.org/TR/activitystreams-core/#naturalLanguageValues">AS2#naturalLanguageValues</a>
 */
public class NaturalValue {
    private final Locale locale;
    private final Map<Locale, String> _map;

    public static NaturalValueBuilder builder() {
        return new NaturalValueBuilder(Locale.getDefault());
    }

    public static NaturalValueBuilder builder(Locale locale) {
        return new NaturalValueBuilder(Objects.requireNonNull(locale));
    }

    private NaturalValue(Locale locale) {
        this.locale = locale;
        this._map = new HashMap<>();
    }

    public static class NaturalValueBuilder {
        private NaturalValue _value;

        private NaturalValueBuilder(Locale locale) {
            _value = new NaturalValue(locale);
        }

        public NaturalValueBuilder withValue(String language, String value) {
            _value.setValue(language, value);
            return this;
        }

        public NaturalValueBuilder withValue(String value) {
            _value.setValue(value);
            return this;
        }

        public NaturalValue build() {
            return _value;
        }
    }

    public String getValue() {
        return getValue(locale);
    }

    public String getValue(String language) {
        return getValue(Locale.forLanguageTag(language));
    }

    public String getValue(Locale locale) {
        return _map.get(locale);
    }

    public void setValue(String value) {
        setValue(locale, value);
    }

    public void setValue(String language, String text) {
        setValue(Locale.forLanguageTag(language), text);
    }

    public void setValue(Locale locale, String text) {
        _map.put(locale, text);
    }

    public boolean hasValueForLanguage(Locale locale) {
        return _map.containsKey(locale);
    }

    public boolean hasMultipleLanguages() {
        return _map.size() > 1;
    }

    public Set<Map.Entry<Locale, String>> getAllValues() {
        return _map.entrySet();
    }

    @Override
    public String toString() {
        return getValue();
    }
}