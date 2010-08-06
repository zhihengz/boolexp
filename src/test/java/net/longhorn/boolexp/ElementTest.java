package net.longhorn.boolexp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;
/**
 * Unit test for simple App.
 */
public class ElementTest extends MockObjectTestCase {

    VariableProvider mockvp = mock( VariableProvider.class );

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ElementTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( ElementTest.class );
    }

    public void testIntegers() {

	Element a = new Element( 1 );
	Element b = new Element( 2 );
	
	assertTrue( b.gt( a, null ) );
	assertFalse( b.lt( a, null ) );
	assertFalse( b.eq( a, null ) );

	assertTrue( a.lt( b, null ) );
	assertFalse( a.eq( b, null ) );
	assertFalse( a.gt( b, null ) );

	Element c = new Element( 1 );

	assertTrue( c.eq( a , null ) );
	assertFalse( c.gt( a , null ) );
	assertFalse( c.lt( a , null ) );

	assertTrue( a.eq( c , null ) );
	assertFalse( a.gt( c , null ) );
	assertFalse( a.lt( c , null ) );
    }
    public void testStrings() {

	Element a = new Element( "aa" );
	Element b = new Element( "bb" );
	
	assertTrue( b.gt( a, null ) );
	assertFalse( b.lt( a, null ) );
	assertFalse( b.eq( a, null ) );

	assertTrue( a.lt( b, null ) );
	assertFalse( a.eq( b, null ) );
	assertFalse( a.gt( b, null ) );

	Element c = new Element( "aa" );

	assertTrue( c.eq( a , null ) );
	assertFalse( c.gt( a , null ) );
	assertFalse( c.lt( a , null ) );

	assertTrue( a.eq( c , null ) );
	assertFalse( a.gt( c , null ) );
	assertFalse( a.lt( c , null ) );
    }
    public void testMixIntegerAndString( ) {

	Element a = new Element( 1 );
	Element b = new Element( "2" );
	
	assertTrue( b.gt( a, null ) );
	assertFalse( b.lt( a, null ) );
	assertFalse( b.eq( a, null ) );

	assertTrue( a.lt( b, null ) );
	assertFalse( a.eq( b, null ) );
	assertFalse( a.gt( b, null ) );

    }

    public void testVariables( ) {

	Variable var = new Variable( "foo" );
	checking( new Expectations( )  {
		{
		    one( mockvp ).interpret( "foo" );
		    will( returnValue( new Integer( 2 ) ) );

		    one( mockvp ).interpret( "foo" );
		    will( returnValue( new Integer( 2 ) ) );
		}
	    } );

	Element a = new Element ( 1 );
	Element b = new Element( var );

	assertTrue( b.gt( a, mockvp ) );
	assertTrue( a.lt( b, mockvp ) );
    }
}
