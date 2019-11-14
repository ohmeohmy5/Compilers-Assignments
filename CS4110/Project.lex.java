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


class Yylex {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

	private Trie trie = new Trie();
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int yy_state_dtrans[] = {
		0,
		58
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NOT_ACCEPT,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NOT_ACCEPT,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NOT_ACCEPT,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NOT_ACCEPT,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"1,5:7,1:2,3,5:2,2,5:18,1,36,53,5:2,32,37,5,42,43,6,30,40,31,41,4,48,49:9,5," +
"39,33,34,35,5:2,51:4,52,51,55:17,50,55:2,44,54,45,5,56,5,11,7,15,17,10,21,2" +
"7,28,22,55,14,9,23,12,8,24,55,13,16,20,18,29,25,19,26,55,46,38,47,5:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,174,
"0,1,2,1,3,1,4,1:3,5,6,7,8,1:9,9,1,10,1:6,11,1:2,12,10,13,10:20,14,1,15,16,1" +
"7,18,19,20,21,22,23,24,25,26,27,19,28,29,30,31,23,32,33,34,35,36,37,38,39,4" +
"0,41,42,43,44,45,46,47,48,18,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,6" +
"4,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,10,8" +
"8,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109," +
"110,111,112,113,114,10,115,116,117,118,119,120,121,122,123")[0];

	private int yy_nxt[][] = unpackFromString(124,57,
"1,2,60,3,4,-1,5,6,164:2,132,164,110,167,164,168,169,170,164:2,133,111,61,16" +
"4,171,172,164:3,134,7,8,9,10,11,12,13,66,70,14,15,16,17,18,19,20,21,22,23,6" +
"2,164:3,73,-1,164,-1:59,2,-1:59,76,-1,24,-1:57,164,173,164:4,135,164:16,-1:" +
"18,136:2,164:3,-1:2,164,136,-1:34,26,-1:56,27,-1:56,28,-1:56,29,-1:41,78,-1" +
":21,32,-1:8,78,-1:13,164:23,-1:18,136:2,164:3,-1:2,164,136,-1:10,86,-1:37,3" +
"2:2,-1:2,86,-1:11,164:4,155,164:18,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:" +
"3,166,164:19,-1:18,136:2,164:3,-1:2,164,136,1,65,69,3,98,65,100,65:50,-1:3," +
"3,-1:60,164:5,74,164:8,25,164,143,164:6,-1:18,136:2,164:3,-1:2,164,136,-1:4" +
"1,32,-1:6,62:2,-1:55,63:2,-1:8,73:2,-1,73:49,33,80,73:2,-1,65:2,-1,98,65,10" +
"2,65:50,-1:37,30,-1:26,164:18,35,164:4,-1:18,136:2,164:3,-1:2,164,136,-1:7," +
"68,-1:2,68:2,-1:3,68,-1,68,-1:3,68,-1:26,68:2,-1,68:2,-1:5,65:2,3,98,65,102" +
",65:50,-1:38,31,-1:25,164:6,36,164:16,-1:18,136:2,164:3,-1:2,164,136,-1,65:" +
"2,-1,72,65,102,65:50,-1:7,164:13,37,164:9,-1:18,136:2,164:3,-1:2,164,136,-1" +
",65:2,-1,98,65,75,65:50,-1,76:2,34,82,76,84,76:50,-1:7,164:3,38,164:19,-1:1" +
"8,136:2,164:3,-1:2,164,136,-1:7,164:2,39,164:20,-1:18,136:2,164:3,-1:2,164," +
"136,-1,88:2,90,73:49,64,80,73:2,-1:7,164:13,40,164:9,-1:18,136:2,164:3,-1:2" +
",164,136,-1,76:2,-1,92,76,-1,76:50,-1:7,164:3,41,164:19,-1:18,136:2,164:3,-" +
"1:2,164,136,-1,76:2,-1:2,76,94,76:50,-1:7,164:9,42,164:13,-1:18,136:2,164:3" +
",-1:2,164,136,-1:30,96:2,-1:16,63:2,-1:14,164:10,43,164:12,-1:18,136:2,164:" +
"3,-1:2,164,136,-1,88:2,90,73:49,33,80,73:2,-1:7,164:7,44,164:15,-1:18,136:2" +
",164:3,-1:2,164,136,-1,90:3,-1:50,73,-1:9,164:9,45,164:13,-1:18,136:2,164:3" +
",-1:2,164,136,-1,76:2,34,92,76,84,76:50,-1:7,164:3,46,164:19,-1:18,136:2,16" +
"4:3,-1:2,164,136,-1,76:2,34,82,76,94,76:50,-1:7,164:3,47,164:19,-1:18,136:2" +
",164:3,-1:2,164,136,-1:7,164:5,48,164:17,-1:18,136:2,164:3,-1:2,164,136,-1," +
"65:2,-1,72,65,-1,65:50,-1:7,164:5,49,164:17,-1:18,136:2,164:3,-1:2,164,136," +
"-1,65:2,-1,59,65,75,65:50,-1:7,164:20,50,164:2,-1:18,136:2,164:3,-1:2,164,1" +
"36,-1,65:2,-1:2,65,75,65:50,-1:7,164:3,51,164:19,-1:18,136:2,164:3,-1:2,164" +
",136,-1:7,164:5,52,164:17,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:9,53,164:" +
"13,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:5,54,164:17,-1:18,136:2,164:3,-1" +
":2,164,136,-1:7,164:19,55,164:3,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:3,5" +
"6,164:19,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:9,57,164:13,-1:18,136:2,16" +
"4:3,-1:2,164,136,-1:7,164:3,67,164:7,113,164:11,-1:18,136:2,164:3,-1:2,164," +
"136,-1:7,164,71,164:2,142,164:18,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:9," +
"77,164:13,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:2,79,164:20,-1:18,136:2,1" +
"64:3,-1:2,164,136,-1:7,164:9,81,164:13,-1:18,136:2,164:3,-1:2,164,136,-1:7," +
"164:11,83,164:11,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:15,85,164:7,-1:18," +
"136:2,164:3,-1:2,164,136,-1:7,164:15,87,164:7,-1:18,136:2,164:3,-1:2,164,13" +
"6,-1:7,164:4,89,164:18,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:9,91,164:13," +
"-1:18,136:2,164:3,-1:2,164,136,-1:7,164:9,93,164:13,-1:18,136:2,164:3,-1:2," +
"164,136,-1:7,164:2,95,164:20,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:2,97,1" +
"64:20,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:6,99,164:16,-1:18,136:2,164:3" +
",-1:2,164,136,-1:7,164:5,101,164:17,-1:18,136:2,164:3,-1:2,164,136,-1:7,164" +
":2,103,164:20,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:4,104,164:18,-1:18,13" +
"6:2,164:3,-1:2,164,136,-1:7,164:10,105,164:12,-1:18,136:2,164:3,-1:2,164,13" +
"6,-1:7,164:2,106,164:20,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:4,107,164:1" +
"8,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:8,108,164:14,-1:18,136:2,164:3,-1" +
":2,164,136,-1:7,164:13,109,164:9,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:2," +
"112,164:9,137,164:10,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:3,114,164:2,11" +
"5,164:14,116,164,-1:18,136:2,164:3,-1:2,164,136,-1:7,164,117,164:21,-1:18,1" +
"36:2,164:3,-1:2,164,136,-1:7,164:3,118,164:19,-1:18,136:2,164:3,-1:2,164,13" +
"6,-1:7,164:13,147,164:9,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:4,148,164:8" +
",149,164:9,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:4,119,164:18,-1:18,136:2" +
",164:3,-1:2,164,136,-1:7,164:6,150,164:16,-1:18,136:2,164:3,-1:2,164,136,-1" +
":7,164:11,151,164:11,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:2,120,164:20,-" +
"1:18,136:2,164:3,-1:2,164,136,-1:7,164:17,165,164:5,-1:18,136:2,164:3,-1:2," +
"164,136,-1:7,164:15,152,164:7,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:15,12" +
"1,164:7,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:2,153,164:20,-1:18,136:2,16" +
"4:3,-1:2,164,136,-1:7,164:3,154,164:19,-1:18,136:2,164:3,-1:2,164,136,-1:7," +
"164:10,122,164:12,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:11,123,164:11,-1:" +
"18,136:2,164:3,-1:2,164,136,-1:7,164:15,124,164:7,-1:18,136:2,164:3,-1:2,16" +
"4,136,-1:7,125,164:22,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:5,157,164:17," +
"-1:18,136:2,164:3,-1:2,164,136,-1:7,164:3,126,164:19,-1:18,136:2,164:3,-1:2" +
",164,136,-1:7,164:5,127,164:17,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:6,15" +
"8,164:16,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:3,160,164:19,-1:18,136:2,1" +
"64:3,-1:2,164,136,-1:7,164:13,128,164:9,-1:18,136:2,164:3,-1:2,164,136,-1:7" +
",164:6,129,164:16,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:14,161,164:8,-1:1" +
"8,136:2,164:3,-1:2,164,136,-1:7,164:16,162,164:6,-1:18,136:2,164:3,-1:2,164" +
",136,-1:7,164:4,130,164:18,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:3,163,16" +
"4:19,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:5,131,164:17,-1:18,136:2,164:3" +
",-1:2,164,136,-1:7,164:2,156,164:20,-1:18,136:2,164:3,-1:2,164,136,-1:7,164" +
":6,159,164:16,-1:18,136:2,164:3,-1:2,164,136,-1:7,164:3,138,164:19,-1:18,13" +
"6:2,164:3,-1:2,164,136,-1:7,164:2,139,164:20,-1:18,136:2,164:3,-1:2,164,136" +
",-1:7,164:13,140,164:9,-1:18,136:2,164:3,-1:2,164,136,-1:7,164,141,164:21,-" +
"1:18,136:2,164:3,-1:2,164,136,-1:7,164:6,144,164:16,-1:18,136:2,164:3,-1:2," +
"164,136,-1:7,164:21,145,164,-1:18,136:2,164:3,-1:2,164,136,-1:7,164,146,164" +
":21,-1:18,136:2,164:3,-1:2,164,136");

	public Yytoken yylex ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {
				return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{}
					case -3:
						break;
					case 3:
						{System.out.println();}
					case -4:
						break;
					case 4:
						{ return (new Yytoken(26,yytext(),yyline,yychar,yychar+1)); }
					case -5:
						break;
					case 5:
						{ return (new Yytoken(25,yytext(),yyline,yychar,yychar+1)); }
					case -6:
						break;
					case 6:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -7:
						break;
					case 7:
						{ return (new Yytoken(23,yytext(),yyline,yychar,yychar+1)); }
					case -8:
						break;
					case 8:
						{ return (new Yytoken(24,yytext(),yyline,yychar,yychar+1)); }
					case -9:
						break;
					case 9:
						{ return (new Yytoken(27,yytext(),yyline,yychar,yychar+1)); }
					case -10:
						break;
					case 10:
						{ return (new Yytoken(28,yytext(),yyline,yychar,yychar+1)); }
					case -11:
						break;
					case 11:
						{ return (new Yytoken(37,yytext(),yyline,yychar,yychar+1)); }
					case -12:
						break;
					case 12:
						{ return (new Yytoken(30,yytext(),yyline,yychar,yychar+1)); }
					case -13:
						break;
					case 13:
						{ return (new Yytoken(36,yytext(),yyline,yychar,yychar+1)); }
					case -14:
						break;
					case 14:
						{ return (new Yytoken(38,yytext(),yyline,yychar,yychar+1)); }
					case -15:
						break;
					case 15:
						{ return (new Yytoken(39,yytext(),yyline,yychar,yychar+1)); }
					case -16:
						break;
					case 16:
						{ return (new Yytoken(40,yytext(),yyline,yychar,yychar+1)); }
					case -17:
						break;
					case 17:
						{ return (new Yytoken(41,yytext(),yyline,yychar,yychar+1)); }
					case -18:
						break;
					case 18:
						{ return (new Yytoken(42,yytext(),yyline,yychar,yychar+1)); }
					case -19:
						break;
					case 19:
						{ return (new Yytoken(43,yytext(),yyline,yychar,yychar+1)); }
					case -20:
						break;
					case 20:
						{ return (new Yytoken(44,yytext(),yyline,yychar,yychar+1)); }
					case -21:
						break;
					case 21:
						{ return (new Yytoken(45,yytext(),yyline,yychar,yychar+1)); }
					case -22:
						break;
					case 22:
						{ return (new Yytoken(46,yytext(),yyline,yychar,yychar+1)); }
					case -23:
						break;
					case 23:
						{
	return (new Yytoken(47, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -24:
						break;
					case 24:
						{yybegin(COMMENT);}
					case -25:
						break;
					case 25:
						{
	trie.insert(yytext());
	return (new Yytoken(8, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -26:
						break;
					case 26:
						{ return (new Yytoken(29,yytext(),yyline,yychar,yychar+2)); }
					case -27:
						break;
					case 27:
						{ return (new Yytoken(32,yytext(),yyline,yychar,yychar+2)); }
					case -28:
						break;
					case 28:
						{ return (new Yytoken(31,yytext(),yyline,yychar,yychar+2)); }
					case -29:
						break;
					case 29:
						{ return (new Yytoken(33,yytext(),yyline,yychar,yychar+2)); }
					case -30:
						break;
					case 30:
						{ return (new Yytoken(34,yytext(),yyline,yychar,yychar+2)); }
					case -31:
						break;
					case 31:
						{ return (new Yytoken(35,yytext(),yyline,yychar,yychar+2)); }
					case -32:
						break;
					case 32:
						{
	return (new Yytoken(48, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -33:
						break;
					case 33:
						{
	String str =  yytext().substring(1,yytext().length() - 1);
	return (new Yytoken(49, str,yyline, yychar, yychar + str.length()));
}
					case -34:
						break;
					case 34:
						{}
					case -35:
						break;
					case 35:
						{
	trie.insert(yytext());
	return (new Yytoken(12, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -36:
						break;
					case 36:
						{
	trie.insert(yytext());
	return (new Yytoken(7, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -37:
						break;
					case 37:
						{
	trie.insert(yytext());
	return (new Yytoken(10, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -38:
						break;
					case 38:
						{
	trie.insert(yytext());
	return (new Yytoken(4, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -39:
						break;
					case 39:
						{
	trie.insert(yytext());
	return (new Yytoken(14, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -40:
						break;
					case 40:
						{
	trie.printWordArray();
}
					case -41:
						break;
					case 41:
						{
	trie.insert(yytext());
	return (new Yytoken(20, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -42:
						break;
					case 42:
						{
	trie.insert(yytext());
	return (new Yytoken(19, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -43:
						break;
					case 43:
						{
	trie.insert(yytext());
	return (new Yytoken(21, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -44:
						break;
					case 44:
						{
	trie.insert(yytext());
	return (new Yytoken(1, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -45:
						break;
					case 45:
						{
	trie.insert(yytext());
	return (new Yytoken(2, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -46:
						break;
					case 46:
						{
	trie.insert(yytext());
	return (new Yytoken(6, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -47:
						break;
					case 47:
						{
	trie.insert(yytext());
	return (new Yytoken(22, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -48:
						break;
					case 48:
						{
	trie.insert(yytext());
	return (new Yytoken(16, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -49:
						break;
					case 49:
						{
	trie.insert(yytext());
	return (new Yytoken(17, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -50:
						break;
					case 50:
						{
	trie.insert(yytext());
	return (new Yytoken(18, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -51:
						break;
					case 51:
						{
	trie.insert(yytext());
	return (new Yytoken(3, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -52:
						break;
					case 52:
						{
	trie.insert(yytext());
	return (new Yytoken(0, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -53:
						break;
					case 53:
						{
	trie.insert(yytext());
	return (new Yytoken(5, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -54:
						break;
					case 54:
						{
	trie.insert(yytext());
	return (new Yytoken(15, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -55:
						break;
					case 55:
						{
	trie.insert(yytext());
	return (new Yytoken(13, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -56:
						break;
					case 56:
						{
	trie.insert(yytext());
	return (new Yytoken(11, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -57:
						break;
					case 57:
						{
	trie.insert(yytext());
	return (new Yytoken(9, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -58:
						break;
					case 58:
						{}
					case -59:
						break;
					case 59:
						{yybegin(YYINITIAL);}
					case -60:
						break;
					case 61:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -61:
						break;
					case 62:
						{
	return (new Yytoken(47, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -62:
						break;
					case 63:
						{
	return (new Yytoken(48, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -63:
						break;
					case 64:
						{
	String str =  yytext().substring(1,yytext().length() - 1);
	return (new Yytoken(49, str,yyline, yychar, yychar + str.length()));
}
					case -64:
						break;
					case 65:
						{}
					case -65:
						break;
					case 67:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -66:
						break;
					case 68:
						{
	return (new Yytoken(47, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -67:
						break;
					case 69:
						{}
					case -68:
						break;
					case 71:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -69:
						break;
					case 72:
						{}
					case -70:
						break;
					case 74:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -71:
						break;
					case 75:
						{}
					case -72:
						break;
					case 77:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -73:
						break;
					case 79:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -74:
						break;
					case 81:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -75:
						break;
					case 83:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -76:
						break;
					case 85:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -77:
						break;
					case 87:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -78:
						break;
					case 89:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -79:
						break;
					case 91:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -80:
						break;
					case 93:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -81:
						break;
					case 95:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -82:
						break;
					case 97:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -83:
						break;
					case 99:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -84:
						break;
					case 101:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -85:
						break;
					case 103:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -86:
						break;
					case 104:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -87:
						break;
					case 105:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -88:
						break;
					case 106:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -89:
						break;
					case 107:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -90:
						break;
					case 108:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -91:
						break;
					case 109:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -92:
						break;
					case 110:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -93:
						break;
					case 111:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -94:
						break;
					case 112:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -95:
						break;
					case 113:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -96:
						break;
					case 114:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -97:
						break;
					case 115:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -98:
						break;
					case 116:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -99:
						break;
					case 117:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -100:
						break;
					case 118:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -101:
						break;
					case 119:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -102:
						break;
					case 120:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -103:
						break;
					case 121:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -104:
						break;
					case 122:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -105:
						break;
					case 123:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -106:
						break;
					case 124:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -107:
						break;
					case 125:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -108:
						break;
					case 126:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -109:
						break;
					case 127:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -110:
						break;
					case 128:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -111:
						break;
					case 129:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -112:
						break;
					case 130:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -113:
						break;
					case 131:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -114:
						break;
					case 132:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -115:
						break;
					case 133:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -116:
						break;
					case 134:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -117:
						break;
					case 135:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -118:
						break;
					case 136:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -119:
						break;
					case 137:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -120:
						break;
					case 138:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -121:
						break;
					case 139:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -122:
						break;
					case 140:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -123:
						break;
					case 141:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -124:
						break;
					case 142:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -125:
						break;
					case 143:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -126:
						break;
					case 144:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -127:
						break;
					case 145:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -128:
						break;
					case 146:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -129:
						break;
					case 147:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -130:
						break;
					case 148:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -131:
						break;
					case 149:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -132:
						break;
					case 150:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -133:
						break;
					case 151:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -134:
						break;
					case 152:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -135:
						break;
					case 153:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -136:
						break;
					case 154:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -137:
						break;
					case 155:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -138:
						break;
					case 156:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -139:
						break;
					case 157:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -140:
						break;
					case 158:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -141:
						break;
					case 159:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -142:
						break;
					case 160:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -143:
						break;
					case 161:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -144:
						break;
					case 162:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -145:
						break;
					case 163:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -146:
						break;
					case 164:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -147:
						break;
					case 165:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -148:
						break;
					case 166:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -149:
						break;
					case 167:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -150:
						break;
					case 168:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -151:
						break;
					case 169:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -152:
						break;
					case 170:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -153:
						break;
					case 171:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -154:
						break;
					case 172:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -155:
						break;
					case 173:
						{
	trie.insert(yytext());
	return (new Yytoken(50, yytext(), yyline, yychar, yychar + yytext().length()));
}
					case -156:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
