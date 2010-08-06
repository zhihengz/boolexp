%{
  import java.io.*;
%}

%token LT GT IN LPAREN RPAREN STR AND OR COMMA EQ MINUS UNKNOWN
%token <ival> NUM
%token <sval> STR
%token <vval> VAR

%type <eval> ElmT
%type <ival> NumT
%type <treeNode> BoolExpT BoolExp2T AndExpT OrExpT LtExpT GtExpT EqExpT
%right MINUS

%%

BoolExpT: AndExpT { rootNode = $1; }
| OrExpT { rootNode = $1; }
| BoolExp2T { rootNode = $1; }

AndExpT: BoolExp2T AND BoolExp2T { $$ = NodeFactory.CreateAndNode( $1, $3 ); }
OrExpT: BoolExp2T OR BoolExp2T { $$ = NodeFactory.CreateOrNode( $1, $3 ); }

BoolExp2T: LtExpT { $$ = $1; }
| LPAREN BoolExpT RPAREN { $$ = $2; }
| GtExpT { $$ = $1; }
| EqExpT { $$ = $1; }

LtExpT: ElmT LT ElmT { $$ = NodeFactory.CreateLtNode( $1, $3 ); }

GtExpT: ElmT GT ElmT { $$ = NodeFactory.CreateGtNode( $1, $3 ); }

EqExpT: ElmT EQ ElmT { $$ = NodeFactory.CreateEqNode( $1, $3 ); }

ElmT: NumT { $$ = new Element( $1 ); }
| STR { $$ = new Element( $1 ) ; }
| VAR { $$ = new Element( $1 ) ; }

NumT: NUM { $$ = $1; } | MINUS NUM { $$ = 0 - $2; } 
;
%%

private Yylex lexer;
private Node rootNode;

private int yylex( ) {

  int yyl_return = -1;
  try {
    yylval = new ParserVal( 0 );
    yyl_return = lexer.yylex( );
  } catch( IOException e ) {
    System.err.println( "IO error :" + e );
  }
  return yyl_return;
  
}
void yyerror( String s ) {
  
  System.out.println( "Error:" + s );
}

public Parser( Reader r ) {

  lexer = new Yylex( r, this );
}

public Node getRootNode( ) {

  return rootNode;
}


