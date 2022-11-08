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
import java.util.Optional;

import com.apicatalog.jsonld.lang.Keywords;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Link extends ASType {
    public static final String TYPE = "Link";

    private final URI href;
    private final String rel;
    private final String mediaType;
    private final String hreflang;
    private final Integer height;
    private final Integer width;
    private final ASType preview;

    private Link(final LinkBuilder linkBuilder) {
        this.href = linkBuilder.href;
        this.rel = linkBuilder.rel;
        this.mediaType = linkBuilder.mediaType;
        this.name = linkBuilder.name;
        this.hreflang = linkBuilder.hreflang;
        this.height = linkBuilder.height;
        this.width = linkBuilder.width;
        this.preview = linkBuilder.preview;
    }

    public static class LinkBuilder {
        private final URI href;
        private String rel;
        private String name;
        private String mediaType;
        private String hreflang;
        private Integer height;
        private Integer width;
        private ASType preview;
    
        public LinkBuilder(URI href) {
            this.href = href;
        }

        public LinkBuilder rel(String rel) {
            this.rel = rel;
            return this;
        }

        public LinkBuilder mediaType(String mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public LinkBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LinkBuilder hreflang(String hreflang) {
            this.hreflang = hreflang;
            return this;
        }

        public LinkBuilder height(int height) {
            this.height = height;
            return this;
        }

        public LinkBuilder width(int width) {
            this.width = width;
            return this;
        }

        public LinkBuilder preview(ASType previewObj) {
            // TODO validate this is either a Link or an Object
            this.preview = previewObj;
            return this;
        }

        public Link build() {
            return new Link(this);
        }
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public URI getHref() {
        return href;
    }

    @Override
    public JsonObject toJsonObject() {
        final var builder = Json.createObjectBuilder()
            .add(Keywords.CONTEXT, CONTEXT_VALUE)
            .add(Keywords.TYPE, getType())
            .add(ASProperties.HREF, href.toString());

        Optional.ofNullable(rel).map(r -> builder.add(ASProperties.REL, r));
        Optional.ofNullable(mediaType).map(mt -> builder.add(ASProperties.MEDIA_TYPE, mt));
        Optional.ofNullable(hreflang).map(hl -> builder.add(ASProperties.HREFLANG, hl));
        Optional.ofNullable(height).map(h -> builder.add(ASProperties.HEIGHT, h));
        Optional.ofNullable(width).map(w -> builder.add(ASProperties.WIDTH, w));
        Optional.ofNullable(preview).map(p -> builder.add(ASProperties.PREVIEW, p.toJsonObject()));
        
        return builder.build();
    }
}
