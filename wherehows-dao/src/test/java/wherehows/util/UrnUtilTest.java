/**
 * Copyright 2015 LinkedIn Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package wherehows.util;

import com.linkedin.events.metadata.DataOrigin;
import com.linkedin.events.metadata.DatasetIdentifier;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static wherehows.util.UrnUtil.*;


public class UrnUtilTest {

  @Test
  public void testGetUrnEntity() {
    assertEquals(getUrnEntity(null), null);

    String urn = "urn:li:user:test";
    String entity = getUrnEntity(urn);

    assertEquals(entity, "test");

    urn = "urn:abc";
    entity = getUrnEntity(urn);
    assertEquals(entity, "abc");
  }

  @Test
  public void testToWhDatasetUrn() {

    DatasetIdentifier identifier = new DatasetIdentifier();
    identifier.dataOrigin = DataOrigin.PROD;
    identifier.dataPlatformUrn = "urn:li:dataPlatform:oracle";
    identifier.nativeName = "orc.test";

    String urn = toWhDatasetUrn(identifier);
    assertEquals(urn, "oracle:///orc/test");

    identifier.dataPlatformUrn = "urn:li:dataPlatform:teradata";
    identifier.nativeName = "tera.test";

    urn = toWhDatasetUrn(identifier);
    assertEquals(urn, "teradata:///tera/test");

    identifier.dataPlatformUrn = "urn:li:dataPlatform:hdfs";
    identifier.nativeName = "/data/abc/test";

    urn = toWhDatasetUrn(identifier);
    assertEquals(urn, "hdfs:///data/abc/test");
  }

  @Test
  public void testSplitWhDatasetUrn() {
    String urn = "oracle:///orc/test";
    String[] parts = splitWhDatasetUrn(urn);

    assertEquals(parts.length, 4);
    assertEquals(parts[0], "oracle");
    assertEquals(parts[1], "/orc");
    assertEquals(parts[2], "orc");
    assertEquals(parts[3], "test");

    urn = "hdfs:///data/abc/def/test";
    parts = splitWhDatasetUrn(urn);

    assertEquals(parts.length, 4);
    assertEquals(parts[0], "hdfs");
    assertEquals(parts[1], "/data/abc");
    assertEquals(parts[2], "/data/abc/def");
    assertEquals(parts[3], "test");

    urn = "kafka:///test";
    parts = splitWhDatasetUrn(urn);

    assertEquals(parts.length, 4);
    assertEquals(parts[0], "kafka");
    assertEquals(parts[1], "");
    assertEquals(parts[2], "");
    assertEquals(parts[3], "test");
  }

  @Test
  public void testCoalesce() {
    String first = coalesce(null, "a", "b");

    assertEquals(first, "a");

    Object uno = coalesce(1, null, 2, null);

    assertEquals(uno, 1);
  }

  @Test
  public void testToStringOrNull() {
    assertEquals(toStringOrNull(null), null);

    CharSequence cs = "foo";
    assertEquals(toStringOrNull(cs), "foo");
  }

  @Test
  public void testTrimToLength() {
    assertEquals(trimToLength(null, 2), null);

    String s = "foobar";

    assertEquals(trimToLength(s, 3), "foo");
    assertEquals(trimToLength(s, 6), "foobar");
  }
}
