package org.xbill.DNS.utils.json.resourcerecords;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.xbill.DNS.AAAARecord;
import org.xbill.DNS.Name;

public class AAAARecordDeserializer extends
		AbstractAddressRecordDeserializer<AAAARecord> {
	private static final long serialVersionUID = 9099734965065272864L;

	public AAAARecordDeserializer() {
		super(AAAARecord.class);
	}

	@Override
	protected AAAARecord createRecord(final Name name, final int dclass,
			final long ttl, final ObjectNode recordNode) {
		return new AAAARecord(name, dclass, ttl,
				getNodeAddressValue(recordNode));
	}

	@Override
	protected String getTextualRecordType() {
		return "AAAA";
	}
}
