
package com.mpit.pristine.utility;

import com.google.common.base.CharMatcher;

/**
 *
 * @author najim
 */
public class CharFilter {
    
    private static final CharMatcher ALPHANUMHYPH = CharMatcher.is('-').or(CharMatcher.inRange('0', '9')).or(CharMatcher.inRange('a', 'z')).or(CharMatcher.inRange('A', 'Z')) ;
    
    public static String filerAlphaNumHyph(String unfilteredStr){
       return ALPHANUMHYPH.retainFrom(unfilteredStr).toString() ;
    }
}
