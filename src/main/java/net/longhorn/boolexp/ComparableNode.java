package net.longhorn.boolexp;
/**
 * Implement < > = 
 */
public class ComparableNode implements Node {

    private Element lElement, rElement;
    private char op;

    protected ComparableNode( Element lElement, 
			      Element rElement, 
			      char op ) {

	this.lElement = lElement;
	this.rElement = rElement;
	this.op = op;
    }

    public boolean valuate( VariableProvider vp ) {

	if ( lElement == null && rElement == null )
	    return true;

	if ( lElement == null || rElement == null )
	    return false;

	return lElement.operate( rElement, op,  vp );
    }

}
