package tk.valoeghese.nightwolf.compiler;

public enum ReadMode {
	CLASS, // inside class 
	COMMENT_LINE, // line comment
	COMMENT_MULTI, /* multi line comment */
	DATA_TYPE, // data type e.g. "int", "ifunc", "ival"
	FILE_HEAD, // main file head
	FILE_BODY, // main file body
	FLOAT,
	INTEGER,
	SEQUENCE,
	STRING,
	TUPLE,
	UNQUOTED_STRING;
}
