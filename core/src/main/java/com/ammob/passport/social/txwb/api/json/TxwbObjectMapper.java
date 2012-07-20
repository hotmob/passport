/**
 * 
 */
package com.ammob.passport.social.txwb.api.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.type.JavaType;

/**
 * @author iday
 * 
 */
public class TxwbObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	public TxwbObjectMapper() {
		super();
		this.registerModule(new TxwbModule());
		this.enable(Feature.UNWRAP_ROOT_VALUE);
		this.disable(Feature.FAIL_ON_NULL_FOR_PRIMITIVES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.codehaus.jackson.map.ObjectMapper#_unwrapAndDeserialize(org.codehaus
	 * .jackson.JsonParser, org.codehaus.jackson.type.JavaType,
	 * org.codehaus.jackson.map.DeserializationContext,
	 * org.codehaus.jackson.map.JsonDeserializer)
	 */
	@Override
	protected Object _unwrapAndDeserialize(JsonParser jp, JavaType rootType,
			DeserializationContext ctxt, JsonDeserializer<Object> deser)
			throws IOException, JsonParseException, JsonMappingException {
		Object obj = null;
		String rootName = "data";
		String actualName = jp.getCurrentName();
		if ("ret".equals(actualName)) {
			jp.nextToken();
			if (jp.getValueAsInt() > 0) {
				jp.nextToken();
				jp.nextToken();
				throw new RuntimeException("remote error: " + jp.getText());
			}
		}
		if (jp.getCurrentToken() != JsonToken.FIELD_NAME
				|| !rootName.equals(actualName)) {
			jp.clearCurrentToken();
			jp.nextToken();
			obj = _unwrapAndDeserialize(jp, rootType, ctxt, deser);
		} else {
			jp.nextToken();
			obj = deser.deserialize(jp, ctxt);
		}
		if (obj == null) {
			throw JsonMappingException.from(jp, "Root token '" + rootName
					+ "' does not found for type " + rootType);
		}
		return obj;
	}

}
