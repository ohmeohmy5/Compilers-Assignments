import java_cup.runtime.*;
import java.io.*;

class Main {
	public static void main(String[] argv) throws Exception{
	Reader input = new FileReader(argv[0]);
	parser p = new parser(new Lexer(input));
	p.debug_parse();
    }
}

class Trie {
	public int[] letter;
	public char[] word;
	public int[] next;
	public int nextWordIndex;
	
	public Trie() {
		this.letter = new int[52];
		for(int i = 0; i < this.letter.length; i++) {
			this.letter[i] = -1;
		}
		this.word = new char[200];
		for(int i = 0; i < this.word.length; i++) {
			this.word[i] = ' ';
		}
		this.next = new int[200];
		for(int i = 0; i < this.next.length; i++) {
			this.next[i] = -1;
		}
		this.nextWordIndex = 0;
	}
	
	public void printWordArray() {
		for(int i = 0; i < 20; i++) {
			System.out.print(Character.valueOf((char) (i + 65)) + "\t");
		}
		System.out.println();
		for(int i = 0; i < 20; i++) {
			System.out.print(this.letter[i] + "\t");
		}
		System.out.println();
		for(int i = 0; i < 6; i++) {
			System.out.print(Character.valueOf((char) (i + 65 + 20)) + "\t");
		}
		for(int i = 0; i < 14; i++) {
			System.out.print(Character.valueOf((char) (i + 97)) + "\t");
		}
		System.out.println();
		for(int i = 0; i < 20; i++) {
			System.out.print(this.letter[i + 20] + "\t");
		}
		System.out.println();
		for(int i = 0; i < 12; i++) {
			System.out.print(Character.valueOf((char) (i + 97 + 14)) + "\t");
		}
		System.out.println();
		for(int i = 0; i < 12; i++) {
			System.out.print(this.letter[i + 40] + "\t");
		}
		System.out.println();
		int count = 0;
		while(count < this.word.length) {
			for(int i = 0; i < 20; i++) {
				System.out.print(i + count + "\t");
			}
			System.out.println();
			for(int i = 0; i < 20; i++) {
				System.out.print(this.word[i + count] + "\t");
			}
			System.out.println();
			for(int i = 0; i < 20; i++) {
				System.out.print(this.next[i + count] + "\t");
			}
			System.out.println();
			count+=20;
		}
	}
	
	public boolean contains(String identifier) {
		boolean result = false;
		if(identifier.charAt(0) >= 65 && identifier.charAt(0) < 91) {
		if(this.letter[identifier.charAt(0) - 65] != -1) {
			int wordIndex = this.letter[identifier.charAt(0) - 65];
			int count = 0;
			char[] identifierChar = identifier.toCharArray();
			boolean searching = true;
			while(searching && count < identifierChar.length - 1) {
				if(this.word[wordIndex] == identifierChar[1 + count]) {
					wordIndex++;
					count++;
				}else if(this.word[wordIndex] == '@') {
					if(this.next[wordIndex] != -1) {
						wordIndex = this.next[wordIndex];
					}else {
						searching = false;
					}
				}else {
					if(this.next[wordIndex] != -1) {
						wordIndex = this.next[wordIndex];
					}else {
						searching = false;
					}
				}
			}
			if(count >= identifierChar.length - 1 && this.word[wordIndex] == '@') {
				result = true;
			}
		}
		}
		if(identifier.charAt(0) >= 97 && identifier.charAt(0) < 123) {
		if(this.letter[identifier.charAt(0) - 97 + 26] != -1) {
			int wordIndex = this.letter[identifier.charAt(0) - 97 + 26];
			int count = 0;
			char[] identifierChar = identifier.toCharArray();
			boolean searching = true;
			while(searching && count < identifierChar.length - 1) {
				if(this.word[wordIndex] == identifierChar[1 + count]) {
					wordIndex++;
					count++;
				}else if(this.word[wordIndex] == '@') {
					if(this.next[wordIndex] != -1) {
						wordIndex = this.next[wordIndex];
					}else {
						searching = false;
					}
				}else {
					if(this.next[wordIndex] != -1) {
						wordIndex = this.next[wordIndex];
					}else {
						searching = false;
					}
				}
			}
			if(count >= identifierChar.length - 1 && this.word[wordIndex] == '@') {
				result = true;
			}
		}
		}
		return result;
	}
	
	public void insert(String identifier) {
		if(!this.contains(identifier)) {
			char[] identifierChar = identifier.toCharArray();
			int wordIndex = 0;
			int count = 0;
			boolean searchingLetter = true;
			while(count < 26 && searchingLetter) {
				if(Character.valueOf((char) (count + 65)).equals(identifierChar[0])) {
					//System.out.println(count + 65);
					if(this.letter[count] == -1) {
						this.letter[count] = this.nextWordIndex;
						wordIndex = this.letter[count];
					}else {
						wordIndex = this.letter[count];
					}
					searchingLetter = false;
				}else if(Character.valueOf((char) (count + 97)).equals(identifierChar[0])) {
					//System.out.println(count + 97);
					if(this.letter[count + 26] == -1) {
						this.letter[count + 26] = this.nextWordIndex;
						wordIndex = this.letter[count + 26];
					}else {
						wordIndex = this.letter[count + 26];
					}
					searchingLetter = false;
				}
				count++;
			}
		
			if(this.word[wordIndex] == ' ') {
				for(int i = 0; i < identifierChar.length - 1; i++) {
					this.word[wordIndex + i] = identifierChar[1 + i];
				}
				this.word[wordIndex + identifierChar.length - 1] = '@';
				this.nextWordIndex = wordIndex + identifierChar.length;
			}else {
				boolean searchingWord = true;
				count = 0;
				while(count < identifierChar.length - 1 && wordIndex < this.word.length && searchingWord) {
					//System.out.println(count);
					if(this.word[wordIndex] == identifierChar[1 + count]) {
						wordIndex++;
						count++;
						if(count >= identifierChar.length - 1) {
							boolean findingEnd = true;
							while(findingEnd) {
								if(this.word[wordIndex] == ' ') {
									this.word[wordIndex] = '@';
									this.nextWordIndex = wordIndex + 1;
									findingEnd = false;
								}else {
									if(this.next[wordIndex] != -1) {
										wordIndex = this.next[wordIndex];
									}else {
										this.next[wordIndex] = this.nextWordIndex;
										wordIndex = this.nextWordIndex;
									}
								}
							}
						}
					}else if (this.word[wordIndex] == '@'){
						if(this.next[wordIndex] != -1) {
							wordIndex = this.next[wordIndex];
						}else {
							this.next[wordIndex] = this.nextWordIndex;
							wordIndex = this.nextWordIndex;
						}
					}else if(this.word[wordIndex] == ' '){
						for(int i = 0; i < identifierChar.length - 1 - count; i++) {
							this.word[wordIndex + i] = identifierChar[count + 1 + i];
						}
						this.word[wordIndex + identifierChar.length - 1 - count] = '@';
						this.nextWordIndex = wordIndex + identifierChar.length - count;
						searchingWord = false;
					}else {
						if(this.next[wordIndex] == -1) {
							this.next[wordIndex] = this.nextWordIndex;
							wordIndex = this.nextWordIndex;
							for(int i = 0; i < identifierChar.length - 1 - count; i++) {
								this.word[wordIndex + i] = identifierChar[count + 1 + i];
							}
							boolean findingEnd = true;
							while(findingEnd) {
								wordIndex = wordIndex + identifierChar.length - 1 - count;
								if(this.word[wordIndex] == ' ') {
									this.word[wordIndex] = '@';
									this.nextWordIndex = wordIndex + 1;
									findingEnd = false;
								}else {
									if(this.next[wordIndex] != -1) {
										wordIndex = this.next[wordIndex];
									}else {
										this.next[wordIndex] = this.nextWordIndex;
										wordIndex = this.nextWordIndex;
									}
								}	
							}
							searchingWord = false;
						}else {
							wordIndex = this.next[wordIndex];
						}
					}
				}
			}
		}
	}
}

%%

%class Lexer
%unicode
%cup
%line
%char
%column

%{
	private Trie trie = new Trie();
	
	public static int next_char;
	
	public static void advance() throws java.io.IOException { next_char = System.in.read(); }
	
	public static void init() throws java.io.IOException { advance(); }
	
	StringBuffer string = new StringBuffer();
	private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
      }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
      }
%}

ALPHA=[A-Za-z]
DIGIT=[0-9]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\0]
WHITE_SPACE_CHAR=[\r\n\ \t\b\0]
STRING_TEXT=(\\\"|[^\n\"]|\\{WHITE_SPACE_CHAR}+\\)*
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*

%state COMMENT

%%

<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ {}

<YYINITIAL,COMMENT> \r\n|\n {System.out.println();}

<YYINITIAL> "//"{COMMENT_TEXT}\n {}

<YYINITIAL> "/*" {yybegin(COMMENT);}

<COMMENT> "*/" {yybegin(YYINITIAL);}

<COMMENT> {COMMENT_TEXT} {}

<YYINITIAL>{
	boolean { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.BOOLEAN); }
	break { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.BREAK); }
	class { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.CLASS); }
	double { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.DOUBLE); }
	else { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.ELSE); }
	extends { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.EXTENDS); }
	false { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.BOOLEANCONSTANT); }
	for { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.FOR); }
	if { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.IF); }
	implements { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.IMPLEMENTS); }
	int { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.INT); }
	interface { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.INTERFACE); }
	new { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.NEW); }
	newarray { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.NEWARRAY); }
	null { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.NULL); }
	println { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.PRINTLN); }
	readln { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.READLN); }
	return { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.RETURN); }
	string { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.STRING); }
	this { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.THIS); }
	true { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.BOOLEANCONSTANT); }
	void { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.VOID); }
	while { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.WHILE); }
}

<YYINITIAL> "+" { System.out.print("+ "); return symbol(sym.PLUS); }
<YYINITIAL> "-" { System.out.print("- "); return symbol(sym.MINUS); }
<YYINITIAL> "*" { System.out.print("* "); return symbol(sym.MULTIPLICATION); }
<YYINITIAL> "/" { System.out.print("/ "); return symbol(sym.DIVISION); }
<YYINITIAL> "%" { System.out.print("% "); return symbol(sym.MOD); }
<YYINITIAL> "<" { System.out.print("< "); return symbol(sym.LESS); }
<YYINITIAL> "<=" { System.out.print("<= "); return symbol(sym.LESSEQUAL); }
<YYINITIAL> ">" { System.out.print("> "); return symbol(sym.GREATER); }
<YYINITIAL> ">=" { System.out.print(">= "); return symbol(sym.GREATEREQUAL); }
<YYINITIAL> "==" { System.out.print("= "); return symbol(sym.EQUAL); }
<YYINITIAL> "!=" { System.out.print("!= "); return symbol(sym.NOTEQUAL); }
<YYINITIAL> "&&" { System.out.print("&& "); return symbol(sym.AND); }
<YYINITIAL> "||" { System.out.print("|| "); return symbol(sym.OR); }
<YYINITIAL> "!" { System.out.print("! "); return symbol(sym.NOT); }
<YYINITIAL> "=" { System.out.print("= "); return symbol(sym.ASSIGNOP); }
<YYINITIAL> ";" { System.out.print("; "); return symbol(sym.SEMICOLON); }
<YYINITIAL> ","  { System.out.print(", "); return symbol(sym.COMMA); }
<YYINITIAL> "." { System.out.print(". "); return symbol(sym.PERIOD); }
<YYINITIAL> "("  { System.out.print("( "); return symbol(sym.LEFTPAREN); }
<YYINITIAL> ")" { System.out.print(") "); return symbol(sym.RIGHTPAREN); }
<YYINITIAL> "["  { System.out.print("[ "); return symbol(sym.LEFTBRACKET); }
<YYINITIAL> "]"  { System.out.print("] "); return symbol(sym.RIGHTBRACKET); }
<YYINITIAL> "{" { System.out.print("{ "); return symbol(sym.LEFTBRACE); }
<YYINITIAL> "}" { System.out.print("} "); return symbol(sym.RIGHTBRACE); }

<YYINITIAL>{
	0|[1-9]({DIGIT})*|0[Xx]([0-9A-Fa-f])+ { System.out.print(yytext() + " "); return symbol(sym.INTCONSTANT, yytext()); }
	[1-9]({DIGIT})*\.({DIGIT})*([Ee][-+]?[0-9]+)?|0\.({DIGIT})*([Ee][-+]?[0-9]+)? { System.out.print(yytext() + " "); return symbol(sym.DOUBLECONSTANT, yytext()); }
	\"{STRING_TEXT}\" {String str =  yytext().substring(1,yytext().length() - 1); System.out.print(yytext() + " "); return symbol(sym.STRINGCONSTANT, yytext()); }
	{ALPHA}({ALPHA}|{DIGIT}|_)* { trie.insert(yytext()); System.out.print(yytext() + " "); return symbol(sym.ID); }
}