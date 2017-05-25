package com.github.sjgibbs.xml.nscontext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.XMLConstants;
import java.util.HashMap;
import java.util.Map;

import static com.github.sjgibbs.xml.nscontext.ExampleConstants.ANOTHER_EXAMPLE_NS;
import static com.github.sjgibbs.xml.nscontext.ExampleConstants.EXAMPLE_NS;
import static javax.xml.XMLConstants.XMLNS_ATTRIBUTE;
import static javax.xml.XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
import static javax.xml.XMLConstants.XML_NS_URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Simon
 * @since 25/05/2017
 */
public class GetPrefixTest {

	private HashMapNamespaceContext context;

	@BeforeEach
	void setUp() {
		context = new HashMapNamespaceContext();
	}

	@Test
	void getPrefixShouldThrowExceptionOnNull() {
		assertThrows(IllegalArgumentException.class, () -> context.getPrefix(null));
	}

	@Test
	void getPrefixShouldReturnXmlForXmlNSURI() {
		assertEquals(XMLConstants.XML_NS_PREFIX,context.getPrefix(XML_NS_URI));
	}

	@Test
	void getPrefixShouldReturnXmlnsForXmlnsAttributeNSURI() {
		assertEquals(XMLNS_ATTRIBUTE,context.getPrefix(XMLNS_ATTRIBUTE_NS_URI));
	}

	@Test
	void givenAnEmptyContextGetPrefixShouldReturnNullForUnboundNSURI() {
		assertEquals(null,context.getPrefix(ANOTHER_EXAMPLE_NS));
	}

	@Test
	void givenAMappingGetPrefixShouldReturnNullForUnboundNSURI() {
		Map<String, String> map = new HashMap<>();
		map.put("ex", EXAMPLE_NS);

		context= new HashMapNamespaceContext(map);
		assertEquals(null,context.getPrefix(ANOTHER_EXAMPLE_NS));
	}

	@Test
	void givenExMappingGetPrefixShouldReturnExForExampleNSURI() {
		Map<String, String> map = new HashMap<>();
		map.put("ex", EXAMPLE_NS);

		context= new HashMapNamespaceContext(map);
		assertEquals("ex",context.getPrefix(EXAMPLE_NS));
	}

}
