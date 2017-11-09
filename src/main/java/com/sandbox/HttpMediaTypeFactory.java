package com.sandbox;

import com.google.common.net.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static com.google.common.net.MediaType.ANY_IMAGE_TYPE;
import static java.net.URLConnection.guessContentTypeFromStream;

class HttpMediaTypeFactory {

    private static String readContentTypeFrom(byte[] data) throws IOException {
        return guessContentTypeFromStream(new ByteArrayInputStream(data));
    }

    MediaType getMediaType(byte[] data) throws IOException {
        return Optional.ofNullable(readContentTypeFrom(data))
                       .map(MediaType::parse)
                       .orElse(ANY_IMAGE_TYPE);
    }
}
