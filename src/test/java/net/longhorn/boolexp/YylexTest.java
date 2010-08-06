package net.longhorn.boolexp;

import java.io.*;
import java.util.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class YylexTest extends TestCase {

    private Yylex lexer;
    private Parser parser;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public YylexTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( YylexTest.class );
    }
    public void testEmpty() throws Exception {

	lexer = new Yylex( new StringReader( "" ), new Parser( ) );
	assertTrue( lexer.yylex( ) <= 0 );
    }

    public void testSingleSymbolToken() throws Exception {

	assertFirstNonValueToken( Parser.EQ,  "==" );
	assertFirstNonValueToken( Parser.LT,  "<" );
	assertFirstNonValueToken( Parser.GT,  ">" );
	assertFirstNonValueToken( Parser.IN,  "in" );
	assertFirstNonValueToken( Parser.LPAREN,  "(" );
	assertFirstNonValueToken( Parser.RPAREN,  ")" );
	assertFirstNonValueToken( Parser.AND,  "and" );
	assertFirstNonValueToken( Parser.OR,  "or" );
	assertFirstNonValueToken( Parser.COMMA,  "," );
	assertFirstNonValueToken( Parser.MINUS,  "-" );
    }

    public void testSingleStringToken( ) throws Exception {

	assertFirstStringToken( "" );
	assertFirstStringToken( "hello" );
	assertFirstStringToken( "hello\nhello" );
	assertFirstStringToken( "hello\\hello" );
	assertFirstStringToken( "hello\\\"hello", "hello\"hello");
    }
    public void testSingleVarToken( ) throws Exception {

	assertFirstVarToken( "" );
	assertFirstVarToken( "hello" );
	assertFirstVarToken( "hello.hello" );
	assertFirstVarToken( "hello[2]" );
	assertFirstVarToken( "123" );
    }
    public void testSingleIntegerToken( ) throws Exception {

	for (int i = 0; i < 100; i++ ) {
	    assertFirstIntegerToken( i );
	}
	
    }
    public void testMixTokens( ) throws Exception {

	parser = new Parser( );
	String mixStr = "123 \"hello\" == < > \n ${var} ( \t )";
	lexer = new Yylex( new StringReader( mixStr ), parser );

	assertIntegerToken( 123 );
	assertStringToken( "hello" );
	assertNonValueToken( Parser.EQ );
	assertNonValueToken( Parser.LT );
	assertNonValueToken( Parser.GT );
	assertVarToken( "var" );
	assertNonValueToken( Parser.LPAREN );
	assertNonValueToken( Parser.RPAREN );
	assertNonValueToken( 0 );
    }
    private void assertNonValueToken( int expect ) throws Exception {

	int ret = lexer.yylex( );
	assertEquals( expect, ret );
    }
    private void assertStringToken( String expect )
	throws Exception {

	int ret = lexer.yylex( );
	assertEquals( Parser.STR, ret );
	assertEquals( expect, parser.yylval.sval );
	
    }
    private void assertIntegerToken( int expect )
	throws Exception {

	int ret = lexer.yylex( );
	assertEquals( Parser.NUM, ret );
	assertEquals( expect, parser.yylval.ival );
	
    }
    private void assertVarToken( String expectVarName )
	throws Exception {

	int ret = lexer.yylex( );
	assertEquals( Parser.VAR, ret );
	Variable var = (Variable) parser.yylval.vval;
	assertEquals( expectVarName, var.getName( ) );
	
    }
    private void assertFirstNonValueToken(int expect, String input ) 
	throws Exception {

	parser = new Parser( );
	lexer = new Yylex( new StringReader( input ), parser );
	assertNonValueToken( expect );
    }
    private void assertFirstStringToken( String input ) 
	throws Exception {

	assertFirstStringToken( input, input );
    }
    private void assertFirstStringToken( String input,
					 String desiredVal ) 
	throws Exception {

	parser = new Parser( );
	lexer = new Yylex( new StringReader( "\"" + input + "\""), 
			   parser );
	assertStringToken( desiredVal );
    }
    private void assertFirstVarToken( String input )
	throws Exception {

	parser = new Parser( );
	lexer = new Yylex( new StringReader( "${" + input + "}"), 
			   parser );
	assertVarToken( input );
    }
    private void assertFirstIntegerToken( int val )
	throws Exception {

	parser = new Parser( );
	lexer = new Yylex( new StringReader( "" + val ),
			   parser );
	assertIntegerToken( val );
    }
}
