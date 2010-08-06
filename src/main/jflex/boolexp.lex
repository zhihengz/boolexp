/**
 *  Lexer for Boolean Expression
 */
package net.longhorn.boolexp;

%%

%byaccj

%{
  StringBuffer string = new StringBuffer( );
  private Parser yyparser;

  public Yylex( java.io.Reader r, Parser yyparser) {

    this(r );
    this.yyparser = yyparser;
  }

%}
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
DecInt = 0 | [1-9][0-9]*

%state VAR
%state STRING

%%
<YYINITIAL> {
  "-"		{ return Parser.MINUS; }
  "=="		{ return Parser.EQ; }
  "<"		{ return Parser.LT; }
  ">"		{ return Parser.GT; }
  "in"		{ return Parser.IN; }
  "("		{ return Parser.LPAREN; }
  ")"		{ return Parser.RPAREN; }
  "and"		{ return Parser.AND; }
  "or"		{ return Parser.OR; }
  ","		{ return Parser.COMMA; }
  "\""          { string.setLength(0); yybegin(STRING); }
  "${"          { string.setLength(0); yybegin(VAR); }
  {DecInt}      { yyparser.yylval =
                    new ParserVal( Integer.parseInt( yytext( ) ) );
                  return Parser.NUM; }
  {WhiteSpace}  { /* ignored */ }
}

<STRING> {

  "\""          { yybegin(YYINITIAL); 
                  yyparser.yylval = 
		    new ParserVal( string.toString( ) );
                  return Parser.STR; }

  [^\"\\]+      { string.append( yytext( ) ); }
  "\\\""        { string.append( '\"' ); }
  \\            { string.append( '\\' ); }

}

<VAR> {

  "}"          { yybegin( YYINITIAL );
                 yyparser.yylval =
		   new ParserVal( new Variable( string.toString( ) ) );
		 return Parser.VAR; }


  [^}]+        { string.append( yytext( ) ); }

}
.   { return Parser.UNKNOWN;  }

