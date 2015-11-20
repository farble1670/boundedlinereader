package org.jtb.boundedlinereader;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class BoundedBufferedReaderTest extends TestCase {
  static final String NEWLINE = System.getProperty("line.separator");

  @Override
  protected void setUp() throws Exception {
  }

  @Override
  protected void tearDown() throws Exception {
  }

  public void testReadExact() throws IOException {
    String d = "Hello, reader.";
    String data = d + NEWLINE;

    Reader r = new InputStreamReader(new ByteArrayInputStream(data.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    String s = reader.readLine(data.length());
    assertEquals(d, s);

    s = reader.readLine(data.length());
    assertNull(s);
  }

  public void testReadLess() throws IOException {
    String d = "Hello, reader.";
    String data = d + NEWLINE;

    Reader r = new InputStreamReader(new ByteArrayInputStream(data.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    String s = reader.readLine(5);
    assertEquals("Hello", s);

    s = reader.readLine(data.length());
    assertNull(s);
  }

  public void testReadMore() throws IOException {
    String d = "Hello, reader.";
    String data = d + NEWLINE;

    Reader r = new InputStreamReader(new ByteArrayInputStream(data.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    String s = reader.readLine(100);
    assertEquals(d, s);

    s = reader.readLine(data.length());
    assertNull(s);
  }

  public void testReadMultiMore() throws IOException {
    String d1 = "Hello 1, reader.";
    String d2 = "Hello 2, reader.";

    String data = d1 + NEWLINE + d2 + NEWLINE;

    Reader r = new InputStreamReader(new ByteArrayInputStream(data.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    String s = reader.readLine(100);
    assertEquals(d1, s);

    s = reader.readLine(100);
    assertEquals(d2, s);

    s = reader.readLine(100);
    assertNull(s);
  }

  public void testReadMultiLess() throws IOException {
    String d1 = "Hello 1, reader.";
    String d2 = "Hello 2, reader.";

    String data = d1 + NEWLINE + d2 + NEWLINE;

    Reader r = new InputStreamReader(new ByteArrayInputStream(data.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    String s = reader.readLine(7);
    assertEquals(d1.substring(0, 7), s);

    s = reader.readLine(7);
    assertEquals(d2.substring(0, 7), s);

    s = reader.readLine(7);
    assertNull(s);
  }

  public void testReadNone() throws IOException {
    String d = "Hello, reader.";
    String data = d + NEWLINE;

    Reader r = new InputStreamReader(new ByteArrayInputStream(data.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    String s = reader.readLine(0);
    assertEquals("", s);

    s = reader.readLine(0);
    assertNull(s);
  }

  public void testReadMultiNone() throws IOException {
    String d1 = "Hello, reader 1.";
    String d2 = "Hello, reader 2.";

    String data = d1 + NEWLINE + d2 + NEWLINE;

    Reader r = new InputStreamReader(new ByteArrayInputStream(data.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    String s = reader.readLine(0);
    assertEquals("", s);

    s = reader.readLine(0);
    assertEquals("", s);

    s = reader.readLine(0);
    assertNull(s);
  }

  public void testSkipOne() throws IOException {
    String d = "Hello 1, reader." + NEWLINE + "Hello 2, reader." + NEWLINE + "Hello 3, reader." + NEWLINE + "Hello 4, reader." + NEWLINE + "Hello 5, reader." + NEWLINE;
    Reader r = new InputStreamReader(new ByteArrayInputStream(d.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    int skipped = reader.skipLines(1);
    assertEquals(1, skipped);

    String s = reader.readLine(100);
    assertEquals("Hello 2, reader.", s);
  }

  public void testSkipNone() throws IOException {
    String d = "Hello 1, reader." + NEWLINE + "Hello 2, reader." + NEWLINE + "Hello 3, reader." + NEWLINE + "Hello 4, reader." + NEWLINE + "Hello 5, reader." + NEWLINE;
    Reader r = new InputStreamReader(new ByteArrayInputStream(d.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    int skipped = reader.skipLines(0);
    assertEquals(0, skipped);

    String s = reader.readLine(100);
    assertEquals("Hello 1, reader.", s);
  }

  public void testSkipMulti() throws IOException {
    String d = "Hello 1, reader." + NEWLINE + "Hello 2, reader." + NEWLINE + "Hello 3, reader." + NEWLINE + "Hello 4, reader." + NEWLINE + "Hello 5, reader." + NEWLINE;
    Reader r = new InputStreamReader(new ByteArrayInputStream(d.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    int skipped = reader.skipLines(3);
    assertEquals(3, skipped);

    String s = reader.readLine(100);
    assertEquals("Hello 4, reader.", s);
  }

  public void testSkipExtra() throws IOException {
    String d = "Hello 1, reader." + NEWLINE + "Hello 2, reader." + NEWLINE + "Hello 3, reader." + NEWLINE + "Hello 4, reader." + NEWLINE + "Hello 5, reader." + NEWLINE;
    Reader r = new InputStreamReader(new ByteArrayInputStream(d.getBytes()));
    BoundedBufferedReader reader = new BoundedBufferedReader(r);

    int skipped = reader.skipLines(10);
    assertEquals(5, skipped);

    String s = reader.readLine(100);
    assertNull(s);
  }

}
