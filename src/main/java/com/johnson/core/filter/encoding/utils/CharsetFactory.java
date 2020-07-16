package com.johnson.core.filter.encoding.utils;

import java.nio.charset.Charset;

final class CharsetFactory {

    public static final Charset ISO_8859_1 = lookup("ISO-8859-1");
    public static final Charset UTF_8 = lookup("UTF-8");

    public static Charset getCharset(String enc, Charset defaultValue) {
        Charset charset = lookup(enc);
        return charset != null ? charset : defaultValue;
    }

    // we don't want to serve too much memory.
    private static Charset lookup(String enc) {
        try {
            if (enc != null && Charset.isSupported(enc)) {
                return Charset.forName(enc);
            }
        } catch (IllegalArgumentException ex) {
            // illegal charset name
            // unsupport charset
        }
        return null;
    }

    private CharsetFactory() {
    }

}