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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * @author Guillermo Castro
 * @since 0.0.1
 */
public class JsonTestUtil {
    private JsonTestUtil() {}

    public static String getJsonFromFile(String filename) throws Exception {
        try (var lines = Files
                .lines(Paths
                        .get(JsonTestUtil.class.getClassLoader().getResource(filename).toURI()))) {
            return lines.collect(Collectors.joining(System.lineSeparator()));
        }
    }

}
