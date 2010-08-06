package net.longhorn.boolexp;

public class ParserVal
{
    /**
     * integer value of this 'union'
     */
    public int ival;

    /**
     * double value of this 'union'
     */
    public double dval;

    /**
     * string value of this 'union'
     */
    public String sval;

    /**
     * object value of this 'union'
     */
    //public Object obj;
    public Variable vval  ;
    public Element eval;
    public Node treeNode;

    //#############################################
    //## C O N S T R U C T O R S
    //#############################################
    /**
     * Initialize me without a value
     */
	public ParserVal()
    {
    }
    /**
     * Initialize me as an int
     */
    public ParserVal(int val)
    {
	ival=val;
    }

    /**
     * Initialize me as a double
     */
    public ParserVal(double val)
    {
	dval=val;
    }

    /**
     * Initialize me as a string
     */
    public ParserVal(String val)
    {
	sval=val;
    }

    /**
     * Initialize me as an Object
 
     public ParserVal(Object val)
     {
     obj=val;
     }
    */

    public ParserVal( Variable val ) {
	
	this.vval = val;
    }

    
}//end class

    //#############################################
    //## E N D    O F    F I L E
    //#############################################
