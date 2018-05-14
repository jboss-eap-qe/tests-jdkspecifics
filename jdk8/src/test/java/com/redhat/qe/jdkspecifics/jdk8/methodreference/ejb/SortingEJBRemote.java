package com.redhat.qe.jdkspecifics.jdk8.methodreference.ejb;

import java.util.Comparator;
import javax.ejb.Remote;

/**
 * @author Jan Martiska
 */
@Remote
public interface SortingEJBRemote {

    public String[] sortStringsUsingAComparator(Comparator<String> comparator, String[] strings);

}
