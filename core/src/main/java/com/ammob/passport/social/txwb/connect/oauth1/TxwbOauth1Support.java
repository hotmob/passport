package com.ammob.passport.social.txwb.connect.oauth1;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriUtils;

public class TxwbOauth1Support {
	private TimestampGenerator timestampGenerator = new DefaultTimestampGenerator();

	public MultiValueMap<String, String> commonOAuthParameters(
			String consumerKey, HttpMethod method, String targetUrl,
			MultiValueMap<String, String> additionalParameters,
			String consumerSecret, String tokenSecret) {
		Map<String, String> oauthParameters = new HashMap<String, String>();
		oauthParameters.put("oauth_consumer_key", consumerKey);
		oauthParameters.put("oauth_signature_method", HMAC_SHA1_SIGNATURE_NAME);
		long timestamp = timestampGenerator.generateTimestamp();
		oauthParameters.put("oauth_timestamp", Long.toString(timestamp));
		oauthParameters.put("oauth_nonce",
				Long.toString(timestampGenerator.generateNonce(timestamp)));
		oauthParameters.put("oauth_version", "1.0");

		MultiValueMap<String, String> collectedParameters = new LinkedMultiValueMap<String, String>(
				(int) ((oauthParameters.size() + additionalParameters.size()) / .75 + 1));
		collectedParameters.setAll(oauthParameters);
		collectedParameters.putAll(additionalParameters);
		String baseString = buildBaseString(method,
				getBaseStringUri(encodeTokenUri(targetUrl)),
				collectedParameters);
		String signature = calculateSignature(baseString, consumerSecret,
				tokenSecret);
		collectedParameters.add("oauth_signature", signature);
		return collectedParameters;
	}

	private URI encodeTokenUri(String url) {
		try {
			return new URI(UriUtils.encodeUri(url, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Not a valid url: " + url, e);
		}
	}

	private String calculateSignature(String baseString, String consumerSecret,
			String tokenSecret) {
		String key = oauthEncode(consumerSecret) + "&"
				+ (tokenSecret != null ? oauthEncode(tokenSecret) : "");
		return sign(baseString, key);
	}

	private String getBaseStringUri(URI uri) {
		try {
			// see: http://tools.ietf.org/html/rfc5849#section-3.4.1.2
			return new URI(uri.getScheme(), null, uri.getHost(), getPort(uri),
					uri.getPath(), null, null).toString();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private int getPort(URI uri) {
		if (uri.getScheme().equals("http") && uri.getPort() == 80
				|| uri.getScheme().equals("https") && uri.getPort() == 443) {
			return -1;
		} else {
			return uri.getPort();
		}
	}

	private String sign(String signatureBaseString, String key) {
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1_MAC_NAME);
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(),
					HMAC_SHA1_MAC_NAME);
			mac.init(spec);
			byte[] text = signatureBaseString.getBytes(UTF8_CHARSET_NAME);
			byte[] signatureBytes = mac.doFinal(text);
			signatureBytes = Base64.encode(signatureBytes);
			String signature = new String(signatureBytes, UTF8_CHARSET_NAME);
			return signature;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		} catch (InvalidKeyException e) {
			throw new IllegalStateException(e);
		} catch (UnsupportedEncodingException shouldntHappen) {
			throw new IllegalStateException(shouldntHappen);
		}
	}

	private String buildBaseString(HttpMethod method, String targetUrl,
			MultiValueMap<String, String> collectedParameters) {
		StringBuilder builder = new StringBuilder();
		builder.append(method.name()).append('&')
				.append(oauthEncode(targetUrl)).append('&');
		builder.append(oauthEncode(normalizeParameters(collectedParameters)));
		return builder.toString();
	}

	private String normalizeParameters(
			MultiValueMap<String, String> collectedParameters) {
		// Normalizes the collected parameters for baseString calculation, per
		// http://tools.ietf.org/html/rfc5849#section-3.4.1.3.2
		MultiValueMap<String, String> sortedEncodedParameters = new TreeMultiValueMap<String, String>();
		for (Iterator<Entry<String, List<String>>> entryIt = collectedParameters
				.entrySet().iterator(); entryIt.hasNext();) {
			Entry<String, List<String>> entry = entryIt.next();
			String collectedName = entry.getKey();
			List<String> collectedValues = entry.getValue();
			List<String> encodedValues = new ArrayList<String>(
					collectedValues.size());
			sortedEncodedParameters.put(oauthEncode(collectedName),
					encodedValues);
			for (Iterator<String> valueIt = collectedValues.iterator(); valueIt
					.hasNext();) {
				String value = valueIt.next();
				encodedValues.add(value != null ? oauthEncode(value) : "");
			}
			Collections.sort(encodedValues);
		}
		StringBuilder paramsBuilder = new StringBuilder();
		for (Iterator<Entry<String, List<String>>> entryIt = sortedEncodedParameters
				.entrySet().iterator(); entryIt.hasNext();) {
			Entry<String, List<String>> entry = entryIt.next();
			String name = entry.getKey();
			List<String> values = entry.getValue();
			for (Iterator<String> valueIt = values.iterator(); valueIt
					.hasNext();) {
				String value = valueIt.next();
				paramsBuilder.append(name).append('=').append(value);
				if (valueIt.hasNext()) {
					paramsBuilder.append("&");
				}
			}
			if (entryIt.hasNext()) {
				paramsBuilder.append("&");
			}
		}
		return paramsBuilder.toString();
	}

	private static String oauthEncode(String param) {
		try {
			// See http://tools.ietf.org/html/rfc5849#section-3.6
			byte[] bytes = encode(param.getBytes(UTF8_CHARSET_NAME), UNRESERVED);
			return new String(bytes, "US-ASCII");
		} catch (Exception shouldntHappen) {
			throw new IllegalStateException(shouldntHappen);
		}
	}

	private static byte[] encode(byte[] source, BitSet notEncoded) {
		Assert.notNull(source, "'source' must not be null");
		ByteArrayOutputStream bos = new ByteArrayOutputStream(source.length * 2);
		for (int i = 0; i < source.length; i++) {
			int b = source[i];
			if (b < 0) {
				b += 256;
			}
			if (notEncoded.get(b)) {
				bos.write(b);
			} else {
				bos.write('%');
				char hex1 = Character.toUpperCase(Character.forDigit(
						(b >> 4) & 0xF, 16));
				char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF,
						16));
				bos.write(hex1);
				bos.write(hex2);
			}
		}
		return bos.toByteArray();
	}

	private static final String HMAC_SHA1_SIGNATURE_NAME = "HMAC-SHA1";

	private static final String HMAC_SHA1_MAC_NAME = "HmacSHA1";

	private static final String UTF8_CHARSET_NAME = "UTF-8";

	private static final BitSet UNRESERVED;

	static {
		BitSet alpha = new BitSet(256);
		for (int i = 'a'; i <= 'z'; i++) {
			alpha.set(i);
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			alpha.set(i);
		}
		BitSet digit = new BitSet(256);
		for (int i = '0'; i <= '9'; i++) {
			digit.set(i);
		}
		BitSet unreserved = new BitSet(256);
		unreserved.or(alpha);
		unreserved.or(digit);
		unreserved.set('-');
		unreserved.set('.');
		unreserved.set('_');
		unreserved.set('~');
		UNRESERVED = unreserved;
	}

	static interface TimestampGenerator {

		long generateTimestamp();

		long generateNonce(long timestamp);

	}

	private static class DefaultTimestampGenerator implements
			TimestampGenerator {

		public long generateTimestamp() {
			return System.currentTimeMillis() / 1000;
		}

		public long generateNonce(long timestamp) {
			return timestamp + RANDOM.nextInt();
		}

		static final Random RANDOM = new Random();

	}

	private static class TreeMultiValueMap<K, V> implements
			MultiValueMap<K, V>, Serializable {

		private static final long serialVersionUID = 3801124242820219131L;

		private final Map<K, List<V>> targetMap;

		public TreeMultiValueMap() {
			this.targetMap = new TreeMap<K, List<V>>();
		}

		@SuppressWarnings("unused")
		public TreeMultiValueMap(Map<K, List<V>> otherMap) {
			this.targetMap = new TreeMap<K, List<V>>(otherMap);
		}

		// MultiValueMap implementation

		public void add(K key, V value) {
			List<V> values = this.targetMap.get(key);
			if (values == null) {
				values = new LinkedList<V>();
				this.targetMap.put(key, values);
			}
			values.add(value);
		}

		public V getFirst(K key) {
			List<V> values = this.targetMap.get(key);
			return (values != null ? values.get(0) : null);
		}

		public void set(K key, V value) {
			List<V> values = new LinkedList<V>();
			values.add(value);
			this.targetMap.put(key, values);
		}

		public void setAll(Map<K, V> values) {
			for (Entry<K, V> entry : values.entrySet()) {
				set(entry.getKey(), entry.getValue());
			}
		}

		public Map<K, V> toSingleValueMap() {
			LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<K, V>(
					this.targetMap.size());
			for (Entry<K, List<V>> entry : targetMap.entrySet()) {
				singleValueMap.put(entry.getKey(), entry.getValue().get(0));
			}
			return singleValueMap;
		}

		// Map implementation

		public int size() {
			return this.targetMap.size();
		}

		public boolean isEmpty() {
			return this.targetMap.isEmpty();
		}

		public boolean containsKey(Object key) {
			return this.targetMap.containsKey(key);
		}

		public boolean containsValue(Object value) {
			return this.targetMap.containsValue(value);
		}

		public List<V> get(Object key) {
			return this.targetMap.get(key);
		}

		public List<V> put(K key, List<V> value) {
			return this.targetMap.put(key, value);
		}

		public List<V> remove(Object key) {
			return this.targetMap.remove(key);
		}

		public void putAll(Map<? extends K, ? extends List<V>> m) {
			this.targetMap.putAll(m);
		}

		public void clear() {
			this.targetMap.clear();
		}

		public Set<K> keySet() {
			return this.targetMap.keySet();
		}

		public Collection<List<V>> values() {
			return this.targetMap.values();
		}

		public Set<Entry<K, List<V>>> entrySet() {
			return this.targetMap.entrySet();
		}

		@Override
		public boolean equals(Object obj) {
			return this.targetMap.equals(obj);
		}

		@Override
		public int hashCode() {
			return this.targetMap.hashCode();
		}

		@Override
		public String toString() {
			return this.targetMap.toString();
		}

	}
}