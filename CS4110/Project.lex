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

%{
	private Trie trie = new Trie();
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
	trie.insert(yytext());
	return (new Yytoken(0, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> break {
	trie.insert(yytext());
	return (new Yytoken(1, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> class {
	trie.insert(yytext());
	return (new Yytoken(2, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> double {
	trie.insert(yytext());
	return (new Yytoken(3, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> else {
	trie.insert(yytext());
	return (new Yytoken(4, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> extends {
	trie.insert(yytext());
	return (new Yytoken(5, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> false {
	trie.insert(yytext());
	return (new Yytoken(6, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> for {
	trie.insert(yytext());
	return (new Yytoken(7, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> if {
	trie.insert(yytext());
	return (new Yytoken(8, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> implements {
	trie.insert(yytext());
	return (new Yytoken(9, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> int {
	trie.insert(yytext());
	return (new Yytoken(10, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> interface {
	trie.insert(yytext());
	return (new Yytoken(11, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> new {
	trie.insert(yytext());
	return (new Yytoken(12, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> newarray {
	trie.insert(yytext());
	return (new Yytoken(13, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> null {
	trie.insert(yytext());
	return (new Yytoken(14, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> println {
	trie.insert(yytext());
	return (new Yytoken(15, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> readln {
	trie.insert(yytext());
	return (new Yytoken(16, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> return {
	trie.insert(yytext());
	return (new Yytoken(17, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> string {
	trie.insert(yytext());
	return (new Yytoken(18, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> this {
	trie.insert(yytext());
	return (new Yytoken(19, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> true {
	trie.insert(yytext());
	return (new Yytoken(20, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> void {
	trie.insert(yytext());
	return (new Yytoken(21, yytext(), yyline, yychar, yychar + yytext().length()));
}

<YYINITIAL> while {
	trie.insert(yytext());
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

<YYINITIAL> test {
	trie.printWordArray();
}

<YYINITIAL> {ALPHA}({ALPHA}|{DIGIT}|_)* {
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}

