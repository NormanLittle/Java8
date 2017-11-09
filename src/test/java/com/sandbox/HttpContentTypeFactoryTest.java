package com.sandbox;

import com.google.common.net.MediaType;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.io.Resources.getResource;
import static com.google.common.io.Resources.toByteArray;
import static org.junit.Assert.assertEquals;

public class HttpContentTypeFactoryTest {

    private static final String JPEG = "image.jpg";
    private static final String PNG = "image.png";
    private static final String GIF = "image.gif";

    private HttpMediaTypeFactory testee = new HttpMediaTypeFactory();

    @Test
    public void testJpeg() throws IOException {
        assertEquals(MediaType.JPEG, testee.getMediaType(readBytesFor(JPEG)));
    }

    @Test
    public void testPng() throws IOException {
        assertEquals(MediaType.PNG, testee.getMediaType(readBytesFor(PNG)));
    }

    @Test
    public void testOther() throws IOException {
        assertEquals(MediaType.GIF, testee.getMediaType(readBytesFor(GIF)));
    }

    @Test
    public void testUnrecognised() throws IOException {
        assertEquals(MediaType.ANY_IMAGE_TYPE, testee.getMediaType(new byte[]{}));
    }

    private static byte[] readBytesFor(String imageFile) throws IOException {
        return toByteArray(getResource(imageFile));
    }
}