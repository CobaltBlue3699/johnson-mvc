package com.johnson.core.filter.encoding.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

final class Detector {

    public static Detector newDetector() {
        return new Detector();
    }

    private String expectPath;
    private String expectEncoding;
    private String result;

    private Detector() {
    }

    private String detect(String path, String encoding) {
        if (path == null || path.length() == 0) {
            return path;
        }
        ByteBuffer bytes;
        try {
            bytes = CharsetFactory.ISO_8859_1.newEncoder().encode(CharBuffer.wrap(path));
        } catch (CharacterCodingException ex) {
            // not latin bytes
            // Strings already parsed by the server
            return path;
        }

        // browsers will transfer URI encoding as UTF-8
        // so we try UTF-8 first, then the character encoding set in request attribute
        for (Charset charset : new Charset[]{
            CharsetFactory.UTF_8,
            CharsetFactory.getCharset(encoding, null)
        }) {
            int position = bytes.position();
            try {
                return charset.newDecoder().decode(bytes).toString();
            } catch (CharacterCodingException ex) {
                // rollback
                bytes.position(position);
            }
        }
        return path;
    }

    public String expr(String superPath, String characterEncoding) {
        if (superPath == null || superPath.length() == 0) {
            return superPath;
        }
        String path = result;
        // some servers such as glassfish may change the path during the request
        if (path == null || !superPath.equals(expectPath)
                || (characterEncoding == null
                        ? expectEncoding != null
                        : !characterEncoding.equals(expectEncoding))) {
            path = detect(superPath, characterEncoding);
            result = path;
            expectEncoding = characterEncoding;
            expectPath = superPath;
        }
        return path;
    }
}