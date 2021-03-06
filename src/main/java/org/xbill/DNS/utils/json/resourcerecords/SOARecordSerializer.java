package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xbill.DNS.SOARecord;

import java.io.IOException;

/**
 * Jackson serializer for the {@link org.xbill.DNS.SOARecord} class
 * @author Arnaud Dumont
 */
public class SOARecordSerializer extends AbstractRecordSerializer<SOARecord> {

	public SOARecordSerializer() {
		super(SOARecord.class);
	}

	@Override
	protected void serializeRDataFields(final SOARecord soaRecord,
			final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException,
			JsonGenerationException {
		if (soaRecord.getHost() != null) {
			jsonGenerator.writeStringField("host", soaRecord.getHost()
					.toString());
		}
		if (soaRecord.getAdmin() != null) {
			jsonGenerator.writeStringField("admin", soaRecord.getAdmin()
					.toString());
		}
		jsonGenerator.writeStringField("serial",
				formatNumber(soaRecord.getSerial()));
		jsonGenerator.writeStringField("refresh",
				formatNumber(soaRecord.getRefresh()));
		jsonGenerator.writeStringField("retry",
				formatNumber(soaRecord.getRetry()));
		jsonGenerator.writeStringField("expire",
				formatNumber(soaRecord.getExpire()));
		jsonGenerator.writeStringField("minimum",
				formatNumber(soaRecord.getMinimum()));
	}
}
