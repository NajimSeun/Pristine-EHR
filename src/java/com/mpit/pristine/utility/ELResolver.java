
package com.mpit.pristine.utility;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 *
 * @author najim
 */
public class ELResolver {

    public static String resolve(FacesContext context, String expre) {
        ELContext elContext = context.getELContext();
        ExpressionFactory ef = context.getApplication().getExpressionFactory();
        ValueExpression valueExpression = ef.createValueExpression(elContext, expre, String.class);
        return (String) valueExpression.getValue(elContext);
    }
}
