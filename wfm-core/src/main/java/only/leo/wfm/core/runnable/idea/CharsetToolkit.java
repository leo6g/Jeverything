// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package only.leo.wfm.core.runnable.idea;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class CharsetToolkit {
  public static  Charset getPlatformCharset() {
    String name = System.getProperty("sun.jnu.encoding");
    Charset value = forName(name);
    return value == null ? getDefaultSystemCharset() : value;
  }
  public static Charset getDefaultSystemCharset() {
    return Charset.defaultCharset();
  }
  public static  Charset forName( String name) {
    Charset charset = null;
    if (name != null) {
      try {
        charset = Charset.forName(name);
      }
      catch (IllegalCharsetNameException | UnsupportedCharsetException ignored) { }
    }

    return charset;
  }
}
