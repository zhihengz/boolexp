package net.longhorn.boolexp;

public class NodeFactory {

    public static Node CreateAndNode( Node lNode, Node rNode ) {

	return new LogicNode( lNode, rNode, LogicNode.OP_AND );
    }

    public static Node CreateOrNode( Node lNode, Node rNode ) {

	return new LogicNode( lNode, rNode, LogicNode.OP_OR );
    }

    public static Node CreateLtNode( Element lElement, Element rElement ) {

	return new ComparableNode( lElement, rElement, Element.OP_LT );
    }

    public static Node CreateGtNode( Element lElement, Element rElement ) {


	return new ComparableNode( lElement, rElement, Element.OP_GT );
    }

    public static Node CreateEqNode( Element lElement, Element rElement ) {

	return new ComparableNode( lElement, rElement, Element.OP_EQ );
    }
}
