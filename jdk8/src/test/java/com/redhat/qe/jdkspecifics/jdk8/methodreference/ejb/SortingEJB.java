package com.redhat.qe.jdkspecifics.jdk8.methodreference.ejb;

import java.util.Arrays;
import java.util.Comparator;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
@Stateless
public class SortingEJB implements SortingEJBRemote {

    @Inject
    Logger logger;

    @Override
    public String[] sortStringsUsingAComparator(Comparator<String> comparator, String[] strings) {
        logger.info("COMPARATOR: " + comparator.toString());
        String[] sorted = new String[strings.length];
        System.arraycopy(strings, 0, sorted, 0, strings.length);
        Arrays.sort(sorted, comparator);
        return sorted;
    }

}
