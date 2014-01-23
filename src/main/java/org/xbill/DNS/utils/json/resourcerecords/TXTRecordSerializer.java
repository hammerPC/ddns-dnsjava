package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.TXTRecord;

import java.io.IOException;

public class TXTRecordSerializer extends AbstractRecordSerializer<TXTRecord> {

	public TXTRecordSerializer() {
		super(TXTRecord.class);
	}

	@Override
	protected void serializeRDataFields(final TXTRecord txtRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		String strings = "";
		for (final Object characterString : txtRecord.getStrings()) {
			if (strings.length() > 0) {
				strings += " ";
			}
			strings += escapeCharacterString((String) characterString);
		}
		jsonGenerator.writeStringField("strings", strings);
	}
}
