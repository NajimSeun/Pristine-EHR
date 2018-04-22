
package com.mpit.pristine.utility;

import com.google.common.base.CharMatcher;

/**
 *
 * @author najim
 */
public class InputCleaner {
    
    private static final CharMatcher ALPHA  = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).precomputed() ;

    public  String cleanAlpha(String input ){
       return  ALPHA.retainFrom(input).toString() ;
    }
}
