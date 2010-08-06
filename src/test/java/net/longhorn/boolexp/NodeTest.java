package net.longhorn.boolexp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

/**
 * Unit test for simple App.
 */
public class NodeTest extends MockObjectTestCase {

    private Node mockNode;
    private VariableProvider vp;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NodeTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( NodeTest.class );
    }
    
    public void setUp( ) {

	setupMockObjects( );
    }
    /**
     * Rigourous Test :-)
     */
    public void testAndNode() {

	Node node = NodeFactory.CreateAndNode( mockNode, mockNode );

	checking( new Expectations( ) {

		{
		    // t && t
		    one( mockNode ).valuate( vp );
		    will( returnValue( true ) );

		    one( mockNode ).valuate( vp );
		    will( returnValue( true ) );

		    // t && f
		    one( mockNode ).valuate( vp );
		    will( returnValue( true ) );

		    one( mockNode ).valuate( vp );
		    will( returnValue( false ) );

		    // f && f
		    one( mockNode ).valuate( vp );
		    will( returnValue( false ) );

		    one( mockNode ).valuate( vp );
		    will( returnValue( false ) );

		}
	    } );

	assertTrue( node.valuate( vp ) );
	assertFalse( node.valuate( vp ) );
	assertFalse( node.valuate( vp ) );
    }
    public void testOrNode() {

	Node node = NodeFactory.CreateOrNode( mockNode, mockNode );

	checking( new Expectations( ) {

		{
		    // t && t
		    one( mockNode ).valuate( vp );
		    will( returnValue( true ) );

		    one( mockNode ).valuate( vp );
		    will( returnValue( true ) );

		    // t && f
		    one( mockNode ).valuate( vp );
		    will( returnValue( true ) );

		    one( mockNode ).valuate( vp );
		    will( returnValue( false ) );

		    // f && f
		    one( mockNode ).valuate( vp );
		    will( returnValue( false ) );

		    one( mockNode ).valuate( vp );
		    will( returnValue( false ) );

		}
	    } );

	assertTrue( node.valuate( vp ) );
	assertTrue( node.valuate( vp ) );
	assertFalse( node.valuate( vp ) );
    }

    private void setupMockObjects( ) {

	mockNode = mock( Node.class );
	vp = mock( VariableProvider.class );
    }

    private Element[] createIntegerTestElements( int length ) {

	Element[] elements = new Element[ length ];

	for ( int i = 0; i < elements.length; i++ ) {
	    elements[ i ] = new Element( i );
	}

	return elements;
    }
    private Element[] createStringTestElements( int length ) {

	Element[] elements = new Element[ length ];

	for ( int i = 0; i < elements.length; i++ ) {
	    elements[ i ] = new Element( "" + i );
	}

	return elements;
    }
    public void testComprasisonNode( ) {

	Element[] intdata = createIntegerTestElements( 2 );
	Element[] strdata = createStringTestElements( 2 );

	assertCompare( intdata[0], intdata[1], '<' );
	assertCompare( strdata[0], strdata[1], '<' );

	assertCompare( intdata[1], intdata[1], '=' );
	assertCompare( strdata[1], strdata[1], '=' );

	assertCompare( intdata[1], intdata[0], '>' );
	assertCompare( strdata[1], strdata[0], '>' );

	    
    }

    private void assertCompare( Element lElement,
				Element rElement, 
				char op ) {

	Node node = null;
	switch ( op ) {
	case '<':
	    node = NodeFactory.CreateLtNode( lElement, rElement );
	    break;
	case '=':
	    node = NodeFactory.CreateEqNode( lElement, rElement );
	    break;
	case '>':
	    node = NodeFactory.CreateGtNode( lElement, rElement );
	    break;
	default:
	    
	}

	assertTrue( node.valuate( vp ) ); 


    }
    
}
