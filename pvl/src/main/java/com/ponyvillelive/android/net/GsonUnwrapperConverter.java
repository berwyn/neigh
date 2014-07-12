package com.ponyvillelive.android.net;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by berwyn on 11 / 7 /14.
 */
public class GsonUnwrapperConverter implements Converter {

    private Gson gson;

    public GsonUnwrapperConverter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        InputStreamReader sr = null;
        try {
            sr = new InputStreamReader(body.in(), "UTF-8");
            Object response = gson.fromJson(sr, type);
            Class objectClass = type.getClass();

            // Collection responses
            try {
                return objectClass.getDeclaredField("results").get(response);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }

            // Object responses
            try {
                return objectClass.getDeclaredField("result").get(response);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }

            // Definitely not the object we're expecting
            throw new ConversionException("Invalid response");
        } catch (IOException | JsonParseException e) {
            throw new ConversionException(e);
        } finally {
            if (sr != null) {
                try {
                    sr.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        try {
            return new JsonOutput(gson.toJson(object).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    private static class JsonOutput implements TypedOutput {

        private final byte[] bytes;

        private JsonOutput(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return "UTF-8";
        }

        @Override
        public long length() {
            return bytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(bytes);
        }
    }
}
