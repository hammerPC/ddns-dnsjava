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
import org.xbill.DNS.NAPTRRecord;
import org.xbill.DNS.Name;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NAPTRRecordDeserializerTest {
	@Mock
	private JsonNode mockOrderJsonNode;
	@Mock
	private JsonNode mockPreferenceJsonNode;
	@Mock
	private JsonNode mockFlagsJsonNode;
	@Mock
	private JsonNode mockServiceJsonNode;
	@Mock
	private JsonNode mockRegexpJsonNode;
	@Mock
	private JsonNode mockReplacementJsonNode;
	@Mock
	private JsonNodeFactory mockJsonNodeFactory;

	private NAPTRRecordDeserializer naptrRecordDeserializer;

	private ObjectNode fakeObjectNode;
    private int order = 2;
	private int preference = 3;
	private String flags = "flags";
	private String service = "service";
	private String regexp = "regexp";
	private Name replacementName;

    @Before
	public void setup() throws Throwable {
		fakeObjectNode = new ObjectNode(mockJsonNodeFactory);

		when(mockOrderJsonNode.textValue()).thenReturn(String.valueOf(order));
        when(mockOrderJsonNode.getNodeType()).thenReturn(JsonNodeType.STRING);
		fakeObjectNode.set("order", mockOrderJsonNode);
		when(mockPreferenceJsonNode.textValue()).thenReturn(
				String.valueOf(preference));
        when(mockPreferenceJsonNode.getNodeType()).thenReturn(JsonNodeType.STRING);
		fakeObjectNode.set("preference", mockPreferenceJsonNode);
		when(mockFlagsJsonNode.textValue()).thenReturn(flags);
		fakeObjectNode.set("flags", mockFlagsJsonNode);
		when(mockServiceJsonNode.textValue()).thenReturn(service);
		fakeObjectNode.set("service", mockServiceJsonNode);
		when(mockRegexpJsonNode.textValue()).thenReturn(regexp);
		fakeObjectNode.set("regexp", mockRegexpJsonNode);

        String replacement = "replacement.domain.com.";
        replacementName = Name.fromString(replacement);
		when(mockReplacementJsonNode.textValue()).thenReturn(replacement);
		fakeObjectNode.set("replacement", mockReplacementJsonNode);

		naptrRecordDeserializer = new NAPTRRecordDeserializer();
	}

	@Test
	public void shouldReturnTheExpectedTextualRecordType() throws Exception {
		assertEquals("NAPTR", naptrRecordDeserializer.getTextualRecordType());
	}

	@Test
	public void shouldCreateExpectedRecord() throws Exception {
        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        NAPTRRecord naptrRecord = naptrRecordDeserializer.createRecord(name,
                dclass, ttl, fakeObjectNode);

		assertEquals(name, naptrRecord.getName());
		assertEquals(dclass, naptrRecord.getDClass());
		assertEquals(ttl, naptrRecord.getTTL());
		assertEquals(order, naptrRecord.getOrder());
		assertEquals(preference, naptrRecord.getPreference());
		assertEquals(flags, naptrRecord.getFlags());
		assertEquals(service, naptrRecord.getService());
		assertEquals(regexp, naptrRecord.getRegexp());
		assertEquals(replacementName, naptrRecord.getReplacement());
	}
}
