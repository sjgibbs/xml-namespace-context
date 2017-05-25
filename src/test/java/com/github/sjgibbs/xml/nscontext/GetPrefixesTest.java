package com.github.sjgibbs.xml.nscontext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.XMLConstants;
import java.util.*;

import static com.github.sjgibbs.xml.nscontext.ExampleConstants.ANOTHER_EXAMPLE_NS;
import static com.github.sjgibbs.xml.nscontext.ExampleConstants.EXAMPLE_NS;
import static javax.xml.XMLConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Simon
 * @since 24/05/2017
 */
public class GetPrefixesTest {


	private HashMapNamespaceContext context;

	@BeforeEach
	void setUp() {
		context = new HashMapNamespaceContext();
	}


	@Test
	void getPrefixesShouldThrowExceptionOnNull() {
		assertThrows(IllegalArgumentException.class, () -> context.getPrefixes(null));
	}

	@Test
	void getPrefixesShouldReturnXmlForXmlNSURI() {
		assertThat(XMLConstants.XML_NS_PREFIX, isIn(collectionOf(context.getPrefixes(XML_NS_URI))));
	}

	@SuppressWarnings("unchecked")
	private static <T> Collection<T> collectionOf(Iterator prefixes) {
		List<T> result = new ArrayList<>(5);
		while(prefixes.hasNext()) {
			result.add( (T) prefixes.next());
		}
		return result;
	}

	@Test
	void getPrefixesShouldReturnXmlnsForXmlnsAttributeNSURI() {
		Collection<?> result = collectionOf(context.getPrefixes(XMLNS_ATTRIBUTE_NS_URI));
		assertThat(result, containsInAnyOrder(XMLNS_ATTRIBUTE));
		assertThat(result, hasSize(1));
	}

	@Test
	void givenAnEmptyContextGetPrefixesShouldReturnEmptyIteratorForUnboundNSURI() {
		Collection<?> result = collectionOf(context.getPrefixes(ANOTHER_EXAMPLE_NS));
		assertThat(result, is(empty()));
	}

	@Test
	void givenAMappingGetPrefixesShouldReturnEmptyIteratorForUnboundNSURI() {
		Map<String, String> map = new HashMap<>();
		map.put("ex", EXAMPLE_NS);

		context= new HashMapNamespaceContext(map);

		Collection<?> result = collectionOf(context.getPrefixes(ANOTHER_EXAMPLE_NS));
		assertThat(result, is(empty()));
	}

	@Test
	void givenExMappingGetPrefixesShouldReturnExForExampleNSURI() {
		Map<String, String> map = new HashMap<>();
		map.put("ex", EXAMPLE_NS);

		context= new HashMapNamespaceContext(map);
		Collection<?> result = collectionOf(context.getPrefixes(EXAMPLE_NS));
		assertThat(result, containsInAnyOrder("ex"));
		assertThat(result, hasSize(1));
	}

}