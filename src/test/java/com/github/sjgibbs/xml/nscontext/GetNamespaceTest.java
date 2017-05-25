package com.github.sjgibbs.xml.nscontext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.XMLConstants;
import java.util.HashMap;
import java.util.Map;

import static com.github.sjgibbs.xml.nscontext.ExampleConstants.EXAMPLE_NS;
import static javax.xml.XMLConstants.DEFAULT_NS_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Simon
 * @since 25/05/2017
 */
public class GetNamespaceTest {

	private HashMapNamespaceContext context;

	@BeforeEach
	void setUp() {
		context = new HashMapNamespaceContext();
	}

	@Test
	void getNamespaceURIShouldThrowExceptionOnNull() {
		assertThrows(IllegalArgumentException.class, () -> context.getNamespaceURI(null));
	}

	@Test
	void givenDefaultBySetterGetNShouldReturnDefaultNSForEmptyPrefix() {
		context.setDefaultNSURI(EXAMPLE_NS);
		assertEquals(EXAMPLE_NS,context.getNamespaceURI(DEFAULT_NS_PREFIX));
	}

	@Test
	void givenDefaultByMapGetNamespaceURIShouldReturnDefaultNSForEmptyPrefix() {

		Map<String, String> map = new HashMap<>();
		map.put(DEFAULT_NS_PREFIX, EXAMPLE_NS);

		context= new HashMapNamespaceContext(map);
		assertEquals(EXAMPLE_NS,context.getNamespaceURI(DEFAULT_NS_PREFIX));
	}

	@Test
	void givenExampleBindingGetNamespaceURIShouldReturnBoundExample() {
		Map<String, String> map = new HashMap<>();
		map.put("ex", EXAMPLE_NS);

		context= new HashMapNamespaceContext(map);
		assertEquals(EXAMPLE_NS,context.getNamespaceURI("ex"));
	}

	@Test
	void givenExampleBindingGetNamespaceURIShouldReturnNullNSForUnmatchedPrefix() {
		Map<String, String> map = new HashMap<>();
		map.put("foo", EXAMPLE_NS);

		context= new HashMapNamespaceContext(map);
		assertEquals(XMLConstants.NULL_NS_URI,context.getNamespaceURI("ex"));
	}

	@Test
	void givenEmptyContextGetNamespaceURIShouldReturnNullNSForUnmatchedPrefix() {
		assertEquals(XMLConstants.NULL_NS_URI,context.getNamespaceURI("ex"));
	}

	@Test
	void getNamespaceURIShouldReturnNullXmlNSForXmlPrefix() {
		assertEquals(XMLConstants.XML_NS_URI,context.getNamespaceURI("xml"));
	}

	@Test
	void getNamespaceURIShouldReturnNullXmlnsAttributeNSForXmlnsPrefix() {
		assertEquals(XMLConstants.XMLNS_ATTRIBUTE_NS_URI,context.getNamespaceURI("xmlns"));
	}
}
