package org.jtb.boundedlinereader;

import junit.framework.TestCase;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BoundedLineReaderTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
  }

  @Override
  protected void tearDown() throws Exception {
  }

  public void testReadExact() throws IOException {
    String d = "Hello, reader.";
    String data = d + BoundedLineReader.NEWLINE;

    ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
    BoundedLineReader reader = new BoundedLineReader(input, data.length());

    String s = reader.readLine();
    assertEquals(d, s);

    s = reader.readLine();
    assertNull(s);
  }

  public void testReadUpToNewline() throws IOException {
    String d = "Hello, reader.";
    String data = d + BoundedLineReader.NEWLINE;

    ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
    BoundedLineReader reader = new BoundedLineReader(input, data.length() - BoundedLineReader.NEWLINE.length());

    String s = reader.readLine();
    assertEquals(d, s);

    s = reader.readLine();
    assertNull(s);
  }

  public void testReadLess() throws IOException {
    String d = "Hello, reader.";
    String data = d + BoundedLineReader.NEWLINE;

    ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
    BoundedLineReader reader = new BoundedLineReader(input, d.length() - 1);

    String s = reader.readLine();
    assertEquals("Hello, reader", s);

    s = reader.readLine();
    assertNull(s);

  }

  public void testReadMore() throws IOException {
    String d = "Hello, reader.";
    String data = d + BoundedLineReader.NEWLINE;

    ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
    BoundedLineReader reader = new BoundedLineReader(input, 100);

    String s = reader.readLine();
    assertEquals(d, s);

    s = reader.readLine();
    assertNull(s);
  }

  public void testReadMultiMore() throws IOException {
    String d1 = "Hello, reader 1.";
    String d2 = "Hello, reader 2.";

    String data = d1 + BoundedLineReader.NEWLINE + d2 + BoundedLineReader.NEWLINE;

    ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
    BoundedLineReader reader = new BoundedLineReader(input, 100);

    String s = reader.readLine();
    assertEquals(d1, s);

    s = reader.readLine();
    assertEquals(d2, s);

    s = reader.readLine();
    assertNull(s);
  }

  public void testReadMultiLess() throws IOException {
    String d1 = "Hello 1, reader.";
    String d2 = "Hello 2, reader.";

    String data = d1 + BoundedLineReader.NEWLINE + d2 + BoundedLineReader.NEWLINE;

    ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
    BoundedLineReader reader = new BoundedLineReader(input, 7);

    String s = reader.readLine();
    assertEquals("Hello 1", s);

    s = reader.readLine();
    assertEquals("Hello 2", s);

    s = reader.readLine();
    assertNull(s);
  }

  public void testReadNone() throws IOException {
    String d = "Hello, reader.";
    String data = d + BoundedLineReader.NEWLINE;

    ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
    BoundedLineReader reader = new BoundedLineReader(input, 0);

    String s = reader.readLine();
    assertNull(s);
  }

  public void testReadMultiNone() throws IOException {
    String d1 = "Hello, reader 1.";
    String d2 = "Hello, reader 2.";

    String data = d1 + BoundedLineReader.NEWLINE + d2 + BoundedLineReader.NEWLINE;

    ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes());
    BoundedLineReader reader = new BoundedLineReader(input, 0);

    String s = reader.readLine();
    assertNull(s);

    s = reader.readLine();
    assertNull(s);

    s = reader.readLine();
    assertNull(s);
  }

}
