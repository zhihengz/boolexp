package net.longhorn.boolexp;

import java.io.*;
import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ParserTest extends TestCase {

    private static final String
	NAME_VAR_STR = "varstr",
	NAME_VAR_INT = "varint",
	VALUE_VAR_STR = "foo";

    private static final Integer 
	VALUE_VAR_INT = new Integer( 10 );
    
    class TestVariableProvider implements VariableProvider {

	public Object interpret( String varName ) {

	    if ( varName.equals( NAME_VAR_STR ) )
		return VALUE_VAR_STR;
	    else if ( varName.equals( NAME_VAR_INT  ) )
		return VALUE_VAR_INT;
	    
	    return null;
	}
    }
    private Yylex lexer;
    private Parser parser;
    private VariableProvider vp = new TestVariableProvider( );
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ParserTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( ParserTest.class );
    }

    public void testLt( ) {

	assertExpress( true, "10 < 12");
	assertExpress( false, "12 < 10" );
	assertExpress( true, " ${varint} < 12" );
	assertExpress( false, " 12 < ${varint}" );
    }
    public void testGt( ) {

	assertExpress( false, "10 > 12");
	assertExpress( true, "12 > 10" );
	assertExpress( false, " ${varint} > 12" );
	assertExpress( true, " 12 > ${varint}" );
    }
    public void testEq( ) {

	assertExpress( false, "10 == 12");
	assertExpress( true, "10 == 10" );
	assertExpress( false, " ${varint} == 12" );
	assertExpress( true, " 10 == ${varint}" );
	assertExpress( false, "\"aa\" == \"ab\"");
	assertExpress( true, "\"aa\" == \"aa\"");
	assertExpress( true, "\"foo\" == ${varstr}");
    }

    public void testPareness( ) {

	assertExpress( true, "( 10 < 12 )" );
	assertExpress( true, " (( 10 < 12 ) )" );
    }

    public void testAnd( ) {

	assertExpress( true, "( ( 10 < 12 ) and ( 12 < 14 ) )" );
	assertExpress( false, "( ( 10 > 12 ) and ( 12 < 14 ) )" ); 
	assertExpress( true, "( ( 10 < 12 ) and ( 12 < 14 ) and ( 14 < 16 ) )" );
    }
    public void testOr( ) {

	assertExpress( false, "( ( 10 > 12 ) or ( 12 > 14 ) )" );
	assertExpress( true, "( ( 10 > 12 ) or ( 12 < 14 ) )" ); 
	assertExpress( true, "( ( 10 > 12 ) or ( 12 < 14 ) or ( 14 < 12 ) )" ); 
    }
    private void assertExpress( boolean expected, 
				String expression ) {

	parser = new Parser( new StringReader( expression ) );
	parser.yyparse( );
	assertNotNull( parser.getRootNode( ) );
	assertEquals( expected, parser.getRootNode( ).valuate( vp ) );
    }
}

