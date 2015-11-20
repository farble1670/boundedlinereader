package org.jtb.boundedlinereader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class BoundedLineReader extends InputStreamReader {
  static final String NEWLINE = System.getProperty("line.separator");

  private final char[] buffer;

  public BoundedLineReader(InputStream in, int maxLineLength) {
    super(in);
    this.buffer = new char[maxLineLength];
  }

  public String readLine() throws IOException {
    char[] last = new char[NEWLINE.length()];

    int c;
    int i = 0;
    boolean nl = false;
    while ((c = read()) != -1 && i < buffer.length) {
      shiftIn(last, (char) c);
      buffer[i++] = (char) c;
      if (equals(NEWLINE, last)) {
        nl = true;
        break;
      }
    }

    // chomp any newline or newline segment from end of buffer
    for (int j = 0; j < NEWLINE.length() && i > 0; j++) {
      if (buffer[i - 1] == '\n' || buffer[i - 1] == '\r') {
        i--;
      }
    }

    if (i == 0) {
      return null;
    }

    String result = new String(buffer, 0, i);

    if (!nl) {
      // didn't read newline, consume until we read it
      while ((c = read()) != -1) {
        shiftIn(last, (char) c);
        if (equals(NEWLINE, last)) {
          break;
        }
      }
    }

    return result;
  }

  private boolean equals(String s, char[] a) {
    if (a.length != s.length()) {
      return false;
    }
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) != a[i]) {
        return false;
      }
    }
    return true;
  }

  private void shiftIn(char[] a, char c) {
    for (int i = a.length - 1; i >= 1; i--) {
      a[i - 1] = a[i];
    }
    a[a.length - 1] = c;
  }

  public int skipLines(int num) {
    int i = 0;
    try {
      while (readLine() != null && ++i <= num) {
      }
    } catch (IOException e) {
    }
    return i - 1;
  }
}