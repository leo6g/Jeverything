// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package only.leo.wfm.core.runnable.idea;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class BaseInputStreamReader extends InputStreamReader {
  private final InputStream myInputStream;

  public BaseInputStreamReader( InputStream in) {
    super(in);
    myInputStream = in;
  }

  public BaseInputStreamReader( InputStream in,  Charset cs) {
    super(in, cs);
    myInputStream = in;
  }

  @Override
  public void close() throws IOException {
    myInputStream.close(); // close underlying input stream without locking in StreamDecoder.
  }
}