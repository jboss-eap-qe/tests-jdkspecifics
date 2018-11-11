package org.jboss.eap.qe.jdkspecifics.jdk9.collections.cdi;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:mjurc@redhat.com">Michal Jurc</a> (c) 2018 Red Hat, Inc.
 */
public interface CDICollectionsTestBean {

    public <E> Set<E> setOfTwoElements(E element1, E element2);

    public <E> Set<E> setOfManyElements(E... elements);

    public <E> List<E> listOfTwoElements(E element1, E element2);

    public <E> List<E> listOfManyElements(E... elements);

    public <K,V> Map<K,V> mapOfTwoKeysAndTwoValues(K key1, V value1, K key2, V value2);

    public <K,V> Map<K,V> mapOfManyEntries(Map.Entry<? extends K, ? extends V>... entries);

}
