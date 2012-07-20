/**
 * 
 */
package com.ammob.passport.social.txwb.api;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author iday
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TxwbProfileList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1443628585113577656L;
	
	private final boolean hasnext;
	private final List<TxwbProfile> info;
	
	/**
	 * @param hasnext
	 * @param info
	 */
	@JsonCreator
	public TxwbProfileList(
			@JsonProperty("hasnext") boolean hasnext,
			@JsonProperty("hasnext") List<TxwbProfile> info) {
		this.hasnext = hasnext;
		this.info = info;
	}

	/**
	 * @return the hasnext
	 */
	public boolean isHasnext() {
		return hasnext;
	}

	/**
	 * @return the info
	 */
	public List<TxwbProfile> getInfo() {
		return info;
	}

}
