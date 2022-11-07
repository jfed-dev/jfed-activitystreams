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

import java.net.URI;

import com.apicatalog.jsonld.lang.Keywords;

import jakarta.json.Json;

public class Link extends ASType {
    public static final String TYPE = "Link";

    private final URI href;

    public static class LinkBuilder {
        private final URI href;

        public LinkBuilder(URI href) {
            this.href = href;
        }

        public Link build() {
            return new Link(this);
        }
    }

    private Link(final LinkBuilder linkBuilder) {
        this.href = linkBuilder.href;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public URI getHref() {
        return href;
    }

    @Override
    public String toJson() {
        final var builder = Json.createObjectBuilder()
            .add(Keywords.CONTEXT, CONTEXT_VALUE)
            .add(Keywords.TYPE, getType())
            .add(ASProperties.HREF, href.toString());

        return writeJsonObject(builder.build());
    }
}
