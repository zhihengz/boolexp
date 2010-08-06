package net.longhorn.boolexp;

public class Element {

    protected static final char
	OP_LT = '<',
	OP_GT = '>',
	OP_EQ = '=';

    private static final int
	INTEGER = 0,
	STRING = 1,
	VARIABLE = 2;

    private  int ival;
    private  String sval;
    private  Variable vval;
    private  int type;

    public Element( int ival ) {

	this.ival = ival;
	this.type = INTEGER;
    }

    public Element( String sval ) {
	
	this.sval = sval;
	this.type = STRING;
    }

    public Element( Variable vval ) {

	this.vval = vval;
	this.type = VARIABLE;
    }

    public boolean lt( Element element, VariableProvider vp ) {
	return operate( element, OP_LT, vp );
    }
    public boolean gt( Element element, VariableProvider vp ) {
	return operate( element, OP_GT, vp );
    }
    public boolean eq( Element element, VariableProvider vp ) {
	return operate( element, OP_EQ, vp );
    }
    protected boolean operate( Element element, 
			       char op, 
			       VariableProvider vp ) {

	Element thisResult = this.evaluate( vp );
	Element thatResult = element.evaluate( vp );

	if ( thisResult.type == INTEGER &&
	     thatResult.type == INTEGER ) {

	    return operateInteger( thisResult.ival, thatResult.ival, op );
	} else {

	    return operateString( thisResult.getStringValue( ),
				  thatResult.getStringValue( ),
				  op );
	}
    }

    private Element evaluate( VariableProvider vp ) {

	if ( this.type == INTEGER ||
	     this.type == STRING ) 
	    return this;

	Object obj = vp.interpret( vval.getName( ) );

	if ( obj == null )
	    return new Element( "" );

	if ( obj instanceof java.lang.Integer ) {

	    return new Element( ((Integer)obj).intValue( ) );
	}

	return new Element( obj.toString( ) );
	
    }

    private String getStringValue( ) {

	String ret = "";
	switch( this.type ) {

	case INTEGER:
	    ret = "" + ival;
	    break;
	case STRING :
	    ret = sval;
	    break;
	default:
	    ret = "";
	}

	return ret;
    }

    private static boolean operateInteger( int a, int b, char op ) {

	boolean ret = false;

	switch( op ) {

	case OP_LT :
	    ret = a < b;
	    break;
	case OP_GT :
	    ret = a > b;
	    break;
	case OP_EQ :
	    ret = a == b;
	    break;
	default:
	    ret = false;
	}
	return ret;
    }

    private static boolean operateString( String a, String b, char op ) {

	if ( a == null || b == null ) return false;

	int result = a.compareTo( b );

	boolean ret = false;

	switch( op ) {

	case OP_LT :
	    ret = result < 0 ;
	    break;
	case OP_GT :
	    ret = result > 0;
	    break;
	case OP_EQ :
	    ret = result == 0;
	    break;
	default:
	    ret = false;
	}
	return ret;
    }
}
