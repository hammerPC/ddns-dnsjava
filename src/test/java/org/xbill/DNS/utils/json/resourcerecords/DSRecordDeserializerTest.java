package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.DSRecord;
import org.xbill.DNS.Name;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DSRecordDeserializerTest {
	@Mock
	private JsonNode mockAlgorithmJsonNode;
	@Mock
	private JsonNode mockDigestIdJsonNode;
	@Mock
	private JsonNode mockFootprintJsonNode;
	@Mock
	private JsonNode mockDigestJsonNode;
	@Mock
	private JsonNodeFactory mockJsonNodeFactory;

	private DSRecordDeserializer dsRecordDeserializer;

	private ObjectNode fakeObjectNode;
	private Name name;
    private int footprint = 2;
	private int algorithm = 3;
	private int digestId = 4;
	private String digest = "3FF4FFF1FF02FCEF3F1AFDC41C9F0FEF";

	@Before
	public void setup() throws Throwable {
		fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

		name = Name.fromString("test.domain.com.");

		when(mockFootprintJsonNode.textValue()).thenReturn(
				String.valueOf(footprint));
        when(mockFootprintJsonNode.getNodeType()).thenReturn(JsonNodeType.STRING);
		fakeObjectNode.set("footprint", mockFootprintJsonNode);

		when(mockAlgorithmJsonNode.textValue()).thenReturn(
				String.valueOf(algorithm));
        when(mockAlgorithmJsonNode.getNodeType()).thenReturn(JsonNodeType.STRING);
		fakeObjectNode.set("algorithm", mockAlgorithmJsonNode);

		when(mockDigestIdJsonNode.textValue()).thenReturn(
				String.valueOf(digestId));
        when(mockDigestIdJsonNode.getNodeType()).thenReturn(JsonNodeType.STRING);
		fakeObjectNode.set("digestId", mockDigestIdJsonNode);

		when(mockDigestJsonNode.textValue()).thenReturn(digest);
		fakeObjectNode.set("digest", mockDigestJsonNode);

		dsRecordDeserializer = new DSRecordDeserializer();
	}

	@Test
	public void shouldReturnTheExpectedTextualRecordType() throws Exception {
		assertEquals("DS", dsRecordDeserializer.getTextualRecordType());
	}

	@Test
	public void shouldCreateExpectedRecord() throws Exception {
        int dclass = 1;
        long ttl = 3600L;
        DSRecord dsRecord = dsRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);

		assertEquals(name, dsRecord.getName());
		assertEquals(dclass, dsRecord.getDClass());
		assertEquals(ttl, dsRecord.getTTL());
		assertEquals(footprint, dsRecord.getFootprint());
		assertEquals(algorithm, dsRecord.getAlgorithm());
		assertEquals(digestId, dsRecord.getDigestID());
		assertEquals(digest, dsRecord.getTextualDigest());
	}
}
