package org.jboss.eap.qe.jdkspecifics.jdk9.collections.pojo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:mjurc@redhat.com">Michal Jurc</a> (c) 2018 Red Hat, Inc.
 */
public class TestCollectionUtilities {

    public <E> Set<E> setOfTwoElements(E element1, E element2) {
        return Set.of(element1, element2);
    }

    public <E> Set<E> setOfManyElements(E... elements) {
        return Set.of(elements);
    }

    public <E> List<E> listOfTwoElements(E element1, E element2) {
        return List.of(element1, element2);
    }

    public <E> List<E> listOfManyElements(E... elements) {
        return List.of(elements);
    }

    public <K, V> Map<K, V> mapOfTwoKeysAndTwoValues(K key1, V value1, K key2, V value2) {
        return Map.of(key1, value1, key2, value2);
    }

    public <K, V> Map<K, V> mapOfManyEntries(Map.Entry<? extends K, ? extends V>... entries) {
        return Map.ofEntries(entries);
    }

}
