package net.longhorn.boolexp;
/**
 * Implement AND OR
 */
public class LogicNode implements Node {

    public static final String
	OP_AND = "&&",
	OP_OR = "||";

    private Node lNode, rNode;
    private String op;

    protected LogicNode( Node lNode,
			 Node rNode,
			 String op ) {
	
	this.lNode = lNode;
	this.rNode = rNode;
	this.op = op;
    }
    public boolean valuate( VariableProvider vp ) {

	boolean lval = lNode.valuate( vp );
	boolean rval = rNode.valuate( vp );

	boolean ret = false;
	if ( op.equals( OP_AND ) ) {
	    ret = ( lval && rval );
	} else if ( op.equals( OP_OR ) ) {
	    ret = ( lval || rval );
	}

	return ret;
    }
}
