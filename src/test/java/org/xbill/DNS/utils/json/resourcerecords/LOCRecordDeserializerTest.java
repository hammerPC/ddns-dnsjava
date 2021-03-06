package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xbill.DNS.LOCRecord;
import org.xbill.DNS.Name;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LOCRecordDeserializerTest {
	@Mock
	private JsonNode mockSizeJsonNode;
	@Mock
	private JsonNode mockHPrecisionJsonNode;
	@Mock
	private JsonNode mockVPrecisionJsonNode;
	@Mock
	private JsonNode mockLatitudeJsonNode;
	@Mock
	private JsonNode mockLongitudeJsonNode;
	@Mock
	private JsonNode mockAltitudeJsonNode;
	@Mock
	private JsonNodeFactory mockJsonNodeFactory;
	
	private LOCRecordDeserializer locRecordDeserializer;
	
	private ObjectNode fakeObjectNode;
    private String size = "30m";
	private String hPrecision = "1m";
	private String vPrecision = "200m";
	private String latitude = "42 21 54 N";
	private String longitude = "71 6 18 W";
	private String altitude = "-24m";
	
	@Before
	public void setup() throws Throwable {
		fakeObjectNode = new ObjectNode(mockJsonNodeFactory);


		when(mockLatitudeJsonNode.textValue()).thenReturn(
				latitude);
		fakeObjectNode.set("latitude", mockLatitudeJsonNode);
		when(mockLongitudeJsonNode.textValue()).thenReturn(
				longitude);
		fakeObjectNode.set("longitude", mockLongitudeJsonNode);
		when(mockAltitudeJsonNode.textValue()).thenReturn(
				altitude);
		fakeObjectNode.set("altitude", mockAltitudeJsonNode);
		when(mockSizeJsonNode.textValue()).thenReturn(
				size);
		fakeObjectNode.set("size", mockSizeJsonNode);
		when(mockHPrecisionJsonNode.textValue()).thenReturn(
				hPrecision);
		fakeObjectNode.set("hPrecision", mockHPrecisionJsonNode);
		when(mockVPrecisionJsonNode.textValue()).thenReturn(
				vPrecision);
		fakeObjectNode.set("vPrecision", mockVPrecisionJsonNode);

		locRecordDeserializer = new LOCRecordDeserializer();
	}
	
	@Test
	public void shouldReturnTheExpectedTextualRecordType() throws Exception {
		assertEquals("LOC", locRecordDeserializer.getTextualRecordType());
	}

	@Test
	public void shouldCreateExpectedRecord() throws Exception {
        Name name = Name.fromString("test.domain.com.");
        int dclass = 1;
        long ttl = 3600L;
        LOCRecord locRecord = locRecordDeserializer.createRecord(name, dclass,
                ttl, fakeObjectNode);

		assertEquals(name, locRecord.getName());
		assertEquals(dclass, locRecord.getDClass());
		assertEquals(ttl, locRecord.getTTL());
		assertEquals(latitude, locRecord.getTextualLatitude());
		assertEquals(longitude, locRecord.getTextualLongitude());
		assertEquals(altitude, locRecord.getTextualAltitude());
		assertEquals(size, locRecord.getTextualSize());
		assertEquals(hPrecision, locRecord.getTextualHPrecision());
		assertEquals(vPrecision, locRecord.getTextualVPrecision());
	}
}
