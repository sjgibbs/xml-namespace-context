package com.github.sjgibbs.xml.nscontext;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.*;

import static java.util.Collections.unmodifiableMap;
import static javax.xml.XMLConstants.*;

/**
 * @author Simon
 * @since 24/05/2017
 */
public class HashMapNamespaceContext implements NamespaceContext {
    private static final Map<String,String> BAKED_IN_PREFIX_MAPPINGS = unmodifiableBakedInMappings();

    private static Map<String, String> unmodifiableBakedInMappings() {
        Map<String, String> bakedInMappings = new HashMap<>();
        bakedInMappings.put(XML_NS_PREFIX, XML_NS_URI);
        bakedInMappings.put(XMLNS_ATTRIBUTE, XMLNS_ATTRIBUTE_NS_URI);
        return unmodifiableMap(bakedInMappings);
    }

    private Map<String, String> map;

    public HashMapNamespaceContext(Map<String, String> map) {
        this.map = new HashMap<>(map);
        this.map.putAll(BAKED_IN_PREFIX_MAPPINGS);
    }

    public HashMapNamespaceContext() {
        this(Collections.emptyMap());
    }

    public String getNamespaceURI(String prefix) {
        if(prefix==null) {
            throw new IllegalArgumentException("Null prefix");
        }
        if(!map.containsKey(prefix)) {
            return NULL_NS_URI;
        }
        return map.get(prefix);
    }

    public String getPrefix(String namespaceURI) {
			if(namespaceURI==null) {
				throw new IllegalArgumentException("Null NS URI");
			}
			for(Map.Entry<String, String> entry : map.entrySet()) {
				if(namespaceURI.equals(entry.getValue())) {
					return entry.getKey();
				}
			}
			return null;
    }

    public Iterator getPrefixes(String namespaceURI) {
    	if(namespaceURI==null) {
    		throw new IllegalArgumentException("null namespaceURI");
			}
			List<String> results = new ArrayList<>(3);
			for(Map.Entry<String, String> entry : map.entrySet()) {
				if(namespaceURI.equals(entry.getValue())) {
					results.add(entry.getKey());
				}
			}
			return results.iterator();
    }

    public void setDefaultNSURI(String defaultNSUri) {
        map.put(XMLConstants.DEFAULT_NS_PREFIX, defaultNSUri);
    }
}
