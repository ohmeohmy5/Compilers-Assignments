import java.lang.System;

class ToyCompiler {
	public static void main(String argv[]) throws java.io.IOException{
		Yylex yy = new Yylex(System.in);
		Yytoken t;
		boolean stillRunning = true;
		while(stillRunning){
			t = yy.yylex();
			if(t != null){
				if(t.m_index >= 0 && t.m_index < 23){
					if(t.m_index == 6 || t.m_index == 20){
						System.out.print("booleanconstant ");
					}else{
						System.out.print(t.m_text + " ");
					}
				}else if(t.m_index == 23){
					System.out.print("plus ");
				}else if(t.m_index == 24){
					System.out.print("minus ");
				}else if(t.m_index == 25){
					System.out.print("multiplication ");
				}else if(t.m_index == 26){
					System.out.print("division ");
				}else if(t.m_index == 27){
					System.out.print("mod ");
				}else if(t.m_index == 28){
					System.out.print("less ");
				}else if(t.m_index == 29){
					System.out.print("lessequal ");
				}else if(t.m_index == 30){
					System.out.print("greater ");
				}else if(t.m_index == 31){
					System.out.print("greaterequal ");
				}else if(t.m_index == 32){
					System.out.print("equal ");
				}else if(t.m_index == 33){
					System.out.print("notequal ");
				}else if(t.m_index == 34){
					System.out.print("and ");
				}else if(t.m_index == 35){
					System.out.print("or ");
				}else if(t.m_index == 36){
					System.out.print("not ");
				}else if(t.m_index == 37){
					System.out.print("assignop ");
				}else if(t.m_index == 38){
					System.out.print("semicolon ");
				}else if(t.m_index == 39){
					System.out.print("comma ");
				}else if(t.m_index == 40){
					System.out.print("period ");
				}else if(t.m_index == 41){
					System.out.print("leftparen ");
				}else if(t.m_index == 42){
					System.out.print("rightparen ");
				}else if(t.m_index == 43){
					System.out.print("leftbracket ");
				}else if(t.m_index == 44){
					System.out.print("rightbracket ");
				}else if(t.m_index == 45){
					System.out.print("leftbrace ");
				}else if(t.m_index == 46){
					System.out.print("rightbrace ");
				}else if(t.m_index == 47){
					System.out.print("intconstant ");
				}else if(t.m_index == 48){
					System.out.print("doubleconstant ");
				}else if(t.m_index == 49){
					System.out.print("stringconstant ");
				}else if(t.m_index == 50){
					System.out.print("id ");
				}
			}else{
				stillRunning = false;
			}
		}
	}
}

class Yytoken {
	public int m_index;
	public String m_text;
	public int m_line;
	public int m_charBegin;
	public int m_charEnd;
	
	Yytoken(int index, String text, int line, int charBegin, int charEnd){
		m_index = index;
		m_text = new String(text);
		m_line = line;
		m_charBegin = charBegin;
		m_charEnd = charEnd;
	}

	public String toString(){
		return m_index + " ";
	}
}

%%

%{

%}
%line
%char
%state COMMENT

ALPHA=[A-Za-z]
DIGIT=[0-9]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\0]
WHITE_SPACE_CHAR=[\r\n\ \t\b\0]
STRING_TEXT=(\\\"|[^\n\"]|\\{WHITE_SPACE_CHAR}+\\)*
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*

%%

<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ {}

<YYINITIAL,COMMENT> \r\n|\n {System.out.println();}

<YYINITIAL> //{COMMENT_TEXT}\n {}

<YYINITIAL> "/*" {yybegin(COMMENT);}

<COMMENT> "*/" {yybegin(YYINITIAL);}

<COMMENT> {COMMENT_TEXT} {}

<YYINITIAL> boolean {
	return (new Yytoken(0, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> break {
	return (new Yytoken(1, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> class {
	return (new Yytoken(2, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> double {
	return (new Yytoken(3, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> else {
	return (new Yytoken(4, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> extends {
	return (new Yytoken(5, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> false {
	return (new Yytoken(6, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> for {
	return (new Yytoken(7, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> if {
	return (new Yytoken(8, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> implements {
	return (new Yytoken(9, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> int {
	return (new Yytoken(10, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> interface {
	return (new Yytoken(11, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> new {
	return (new Yytoken(12, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> newarray {
	return (new Yytoken(13, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> null {
	return (new Yytoken(14, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> println {
	return (new Yytoken(15, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> readln {
	return (new Yytoken(16, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> return {
	return (new Yytoken(17, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> string {
	return (new Yytoken(18, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> this {
	return (new Yytoken(19, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> true {
	return (new Yytoken(20, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> void {
	return (new Yytoken(21, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> while {
	return (new Yytoken(22, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> "+" { return (new Yytoken(23,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "-" { return (new Yytoken(24,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "*" { return (new Yytoken(25,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "/" { return (new Yytoken(26,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "%" { return (new Yytoken(27,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "<" { return (new Yytoken(28,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "<=" { return (new Yytoken(29,yytext(),yyline,yychar,yychar+2)); }
<YYINITIAL> ">" { return (new Yytoken(30,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> ">=" { return (new Yytoken(31,yytext(),yyline,yychar,yychar+2)); }
<YYINITIAL> "==" { return (new Yytoken(32,yytext(),yyline,yychar,yychar+2)); }
<YYINITIAL> "!=" { return (new Yytoken(33,yytext(),yyline,yychar,yychar+2)); }
<YYINITIAL> "&&" { return (new Yytoken(34,yytext(),yyline,yychar,yychar+2)); }
<YYINITIAL> "||" { return (new Yytoken(35,yytext(),yyline,yychar,yychar+2)); }
<YYINITIAL> "!" { return (new Yytoken(36,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "=" { return (new Yytoken(37,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> ";" { return (new Yytoken(38,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> ","  { return (new Yytoken(39,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "." { return (new Yytoken(40,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "("  { return (new Yytoken(41,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> ")" { return (new Yytoken(42,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "["  { return (new Yytoken(43,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "]"  { return (new Yytoken(44,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "{" { return (new Yytoken(45,yytext(),yyline,yychar,yychar+1)); }
<YYINITIAL> "}" { return (new Yytoken(46,yytext(),yyline,yychar,yychar+1)); }


<YYINITIAL> 0|[1-9]({DIGIT})*|0[Xx]([0-9A-Fa-f])+ {
	return (new Yytoken(47, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> [1-9]({DIGIT})*\.({DIGIT})*([Ee][-+]?[0-9]+)?|0\.({DIGIT})*([Ee][-+]?[0-9]+)? {
	return (new Yytoken(48, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> \"{STRING_TEXT}\" {
	String str =  yytext().substring(1,yytext().length() - 1);
	return (new Yytoken(49, str,yyline, yychar, yychar + str.length()));
}

<YYINITIAL> {ALPHA}({ALPHA}|{DIGIT}|_)* {
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}