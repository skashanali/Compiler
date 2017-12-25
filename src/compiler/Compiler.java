package compiler;

import java.io.*;
import java.util.*;

public class Compiler {

    static boolean flag = false, isLexError = false;
    static ArrayList<Token> tokens = new ArrayList<>();
//    static ArrayList<Token> lexicalTokens = new ArrayList<>();
    static int lineNo = 1;

    static Token validate(String temp, int lineNo, char brace) {
        Token token = new Token();
        if (!"continous".equals(temp) && !"while".equals(temp) && !"agar".equals(temp) && !"magar_agar".equals(temp) && !"magar".equals(temp)
                && !"case".equals(temp) && !"default".equals(temp) && !"break".equals(temp) && !"mould".equals(temp) && !"start".equals(temp)
                && !"empty".equals(temp) && !"true".equals(temp) && !"false".equals(temp) && !"return".equals(temp)) {
            switch (temp.charAt(0)) {
                case '\'':
                    if (DFA_CharacterConstant(temp)) {
                        token.setValues("CHARACTER_CONSTANT", temp, lineNo);
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;
                case '"':
                    if (DFA_StringConstant(temp)) {
                        token.setValues("STRING_CONSTANT", temp, lineNo);
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;

                case '4':
                    if (temp.length() > 1) {
                        if (Character.isDigit(temp.charAt(1)) || temp.charAt(1) == '.') {
                            int i = 1;
                            while (i < temp.length()) {
                                if (temp.charAt(i) == '.') {
                                    flag = true;
                                }
                                i++;
                            }
                            if (flag == false) {
                                if (DFA_IntegerConstant(temp)) {
                                    token.setValues("INTEGER_CONSTANT", temp, lineNo);
                                } else {
                                    token.setValues("LEXICAL_ERROR", temp, lineNo);
                                }
                            } else {
                                if (DFA_FloatConstant(temp)) {
                                    token.setValues("FLOAT_CONSTANT", temp, lineNo);
                                } else {
                                    token.setValues("LEXICAL_ERROR", temp, lineNo);
                                }
                                flag = false;
                            }
                        } else if (DFA_Identifier(temp)) {
                            if (brace == '(') {
                                token.setValues("FUNCTION_CHARACTER_IDENTIFIER", temp, lineNo);
                            } else if (brace == '[') {
                                token.setValues("ARRAY_CHARACTER_IDENTIFIER", temp, lineNo);
                            } else {
                                token.setValues("CHARACTER_IDENTIFIER", temp, lineNo);
                            }
                        } else {
                            token.setValues("LEXICAL_ERROR", temp, lineNo);
                        }
                    } else if (DFA_IntegerConstant(temp)) {
                        token.setValues("INTEGER_CONSTANT", temp, lineNo);
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;

                case '$':
                    if (DFA_Identifier(temp)) {
                        if (brace == '(') {
                            token.setValues("FUNCTION_STRING_IDENTIFIER", temp, lineNo);
                        } else if (brace == '[') {
                            token.setValues("ARRAY_STRING_IDENTIFIER", temp, lineNo);
                        } else {
                            token.setValues("STRING_IDENTIFIER", temp, lineNo);
                        }
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;

                case '~':
                    if (DFA_Identifier(temp)) {
                        if (brace == '(') {
                            token.setValues("FUNCTION_FLOAT_IDENTIFIER", temp, lineNo);
                        } else if (brace == '[') {
                            token.setValues("ARRAY_FLOAT_IDENTIFIER", temp, lineNo);
                        } else {
                            token.setValues("FLOAT_IDENTIFIER", temp, lineNo);
                        }
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;

                case '#':
                    if (DFA_Identifier(temp)) {
                        if (brace == '(') {
                            token.setValues("FUNCTION_INTEGER_IDENTIFIER", temp, lineNo);
                        } else if (brace == '[') {
                            token.setValues("ARRAY_INTEGER_IDENTIFIER", temp, lineNo);
                        } else {
                            token.setValues("INTEGER_IDENTIFIER", temp, lineNo);
                        }
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;

                case '?':
                    if (DFA_Identifier(temp)) {
                        if (brace == '(') {
                            token.setValues("FUNCTION_BOOLEAN_IDENTIFIER", temp, lineNo);
                        } else if (brace == '[') {
                            token.setValues("ARRAY_BOOLEAN_IDENTIFIER", temp, lineNo);
                        } else {
                            token.setValues("BOOLEAN_IDENTIFIER", temp, lineNo);
                        }
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;

                case '.':
                    if (DFA_FloatConstant(temp)) {
                        token.setValues("FLOAT_CONSTANT", temp, lineNo);
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;

                case '0':
                case '1':
                case '2':
                case '3':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if (temp.length() > 1) {
                        int i = 1;
                        while (i < temp.length()) {
                            if (temp.charAt(i) == '.') {
                                flag = true;
                            }
                            i++;
                        }
                        if (flag == false) {
                            if (DFA_IntegerConstant(temp)) {
                                token.setValues("INTEGER_CONSTANT", temp, lineNo);
                            } else {
                                token.setValues("LEXICAL_ERROR", temp, lineNo);
                            }
                        } else {
                            if (DFA_FloatConstant(temp)) {
                                token.setValues("FLOAT_CONSTANT", temp, lineNo);
                            } else {
                                token.setValues("LEXICAL_ERROR", temp, lineNo);
                            }
                            flag = false;
                        }
                    } else if (DFA_IntegerConstant(temp)) {
                        token.setValues("INTEGER_CONSTANT", temp, lineNo);
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;

                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                    if (DFA_SimpleIdentifier(temp)) {
                        if (brace == '(') {
                            token.setValues("FUNCTION_VOID_IDENTIFIER", temp, lineNo);
                        } else if (brace == '{') {
                            token.setValues("STRUCT_IDENTIFIER", temp, lineNo);
                        } else {
                            token.setValues("LEXICAL_ERROR", temp, lineNo);
                        }
                    } else {
                        token.setValues("LEXICAL_ERROR", temp, lineNo);
                    }
                    break;

                case '+':
                case '-':
                    int i = 1;
                    while (i < temp.length()) {
                        if (temp.charAt(i) == '.') {
                            flag = true;
                        }
                        i++;
                    }
                    if (flag == false) {
                        if (DFA_IntegerConstant(temp)) {
                            token.setValues("INTEGER_CONSTANT", temp, lineNo);
                        } else {
                            token.setValues("LEXICAL_ERROR", temp, lineNo);
                        }
                    } else {
                        if (DFA_FloatConstant(temp)) {
                            token.setValues("FLOAT_CONSTANT", temp, lineNo);
                        } else {
                            token.setValues("LEXICAL_ERROR", temp, lineNo);
                        }
                        flag = false;
                    }
                    break;

                default: {
                    token.setValues("LEXICAL_ERROR", temp, lineNo);
                }
                break;
            }
        } else {
            switch (temp) {
                case "continous":
                    token.setValues("FOR", temp, lineNo);

                    break;
                case "while":
                    token.setValues("WHILE", temp, lineNo);

                    break;
                case "agar":
                    token.setValues("IF", temp, lineNo);
                    break;
                case "magar_agar":
                    token.setValues("ELSE_IF", temp, lineNo);
                    break;
                case "magar":
                    token.setValues("ELSE", temp, lineNo);
                    break;
                case "case":
                    token.setValues("CASE", temp, lineNo);
                    break;
                case "default":
                    token.setValues("DEFAULT", temp, lineNo);
                    break;
                case "break":
                    token.setValues("BREAK", temp, lineNo);
                    break;
                case "mould":
                    token.setValues("STRUCT", temp, lineNo);
                    break;
                case "start":
                    token.setValues("MAIN", temp, lineNo);
                    break;
                case "empty":
                    token.setValues("VOID", temp, lineNo);
                    break;
                case "true":
                    token.setValues("BOOLEAN_CONSTANT", temp, lineNo);
                    break;
                case "false":
                    token.setValues("BOOLEAN_CONSTANT", temp, lineNo);
                    break;
                case "return":
                    token.setValues("RETURN", temp, lineNo);
                    break;
                default:
                    token.setValues("LEXICAL_ERROR", temp, lineNo);
                    break;
            }
        }
        return token;
    }

    static Token punctuator(char punc, int lineNo) {
        Token token = new Token();
        switch (punc) {
            case '(':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            case ')':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            case '{':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            case '}':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            case '[':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            case ']':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            case '.':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            case ';':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            case ':':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            case ',':
                token.setValues(String.valueOf(punc), String.valueOf(punc), lineNo);
                break;
            default:
                token.setValues("LEXICAL_ERROR", String.valueOf(punc), lineNo);
                break;
        }
        return token;
    }

    static Token operator(String temp, int lineNo) {
        Token token = new Token();
        switch (temp) {
            case "+":
                token.setValues("ADD_SUB", temp, lineNo);
                break;
            case "-":
                token.setValues("ADD_SUB", temp, lineNo);
                break;
            case "*":
                token.setValues("MUL_DIV", temp, lineNo);
                break;
            case "/":
                token.setValues("MUL_DIV", temp, lineNo);
                break;
            case "%":
                token.setValues("MUL_DIV", temp, lineNo);
                break;
            case "=":
                token.setValues("ASSIGNMENT_OPERATOR", temp, lineNo);
                break;
            case "+=":
                token.setValues("ASSIGNMENT_OPERATOR", temp, lineNo);
                break;
            case "-=":
                token.setValues("ASSIGNMENT_OPERATOR", temp, lineNo);
                break;
            case "*=":
                token.setValues("ASSIGNMENT_OPERATOR", temp, lineNo);
                break;
            case "/=":
                token.setValues("ASSIGNMENT_OPERATOR", temp, lineNo);
                break;
            case "%=":
                token.setValues("ASSIGNMENT_OPERATOR", temp, lineNo);
                break;
            case "++":
                token.setValues("INC_DEC", temp, lineNo);
                break;
            case "--":
                token.setValues("INC_DEC", temp, lineNo);
                break;
            case "&&":
                token.setValues("AND", temp, lineNo);
                break;
            case "||":
                token.setValues("OR", temp, lineNo);
                break;
            case "<":
                token.setValues("RELATIONAL_OPERATOR", temp, lineNo);
                break;
            case ">":
                token.setValues("RELATIONAL_OPERATOR", temp, lineNo);
                break;
            case "<=":
                token.setValues("RELATIONAL_OPERATOR", temp, lineNo);
                break;
            case ">=":
                token.setValues("RELATIONAL_OPERATOR", temp, lineNo);
                break;
            case "==":
                token.setValues("RELATIONAL_OPERATOR", temp, lineNo);
                break;
            case "!=":
                token.setValues("RELATIONAL_OPERATOR", temp, lineNo);
                break;
            case "!":
                token.setValues("UNARY_OPERATOR", temp, lineNo);
                break;
            default:
                token.setValues("LEXICAL_ERROR", temp, lineNo);
                break;
        }
        return token;
    }

    public static void main(String[] args) {
        try {
//            FileReader fr = new FileReader("C:\\Users\\skash\\Desktop\\source.txt"); // for laptop
            FileReader fr = new FileReader("C:\\Users\\Hammad Ali\\Desktop\\source.txt"); // for pc
            BufferedReader br = new BufferedReader(fr);
//            FileWriter fw = new FileWriter("C:\\Users\\skash\\Desktop\\tokens.txt"); // for laptop
            FileWriter fw = new FileWriter("C:\\Users\\Hammad Ali\\Desktop\\tokens.txt"); // for pc
            BufferedWriter bw = new BufferedWriter(fw);
            String temp, code = "";
            int i = 0, j;
            while ((temp = br.readLine()) != null) {
                code += temp;
                code += "\n";
            }
            while (i <= code.length() - 2) {
                temp = "";
                while ((code.charAt(i) != '(' && code.charAt(i) != ')' && code.charAt(i) != '{' && code.charAt(i) != ','
                        && code.charAt(i) != '}' && code.charAt(i) != '[' && code.charAt(i) != ']'
                        && code.charAt(i) != ';' && code.charAt(i) != '\n' && code.charAt(i) != '+'
                        && code.charAt(i) != '-' && code.charAt(i) != '*' && code.charAt(i) != '/'
                        && code.charAt(i) != 32 && code.charAt(i) != 34 && code.charAt(i) != 39
                        && code.charAt(i) != '.' && code.charAt(i) != ':' && code.charAt(i) != '='
                        && code.charAt(i) != '<' && code.charAt(i) != '>' && code.charAt(i) != '&'
                        && code.charAt(i) != '|' && code.charAt(i) != '!' && code.charAt(i) != '%' && code.charAt(i) != '@')
                        && i <= code.length() - 2) {
                    temp += code.charAt(i);
                    i++;
                }
                if (!"".equals(temp) && code.charAt(i) != '.') {
                    tokens.add(validate(temp, lineNo, code.charAt(i)));
                } else if (code.charAt(i) == '.') {
                    if (temp.chars().allMatch(Character::isDigit) || temp == "") {
                        temp += code.charAt(i);
                        i++;
                        while ((code.charAt(i) != '(' && code.charAt(i) != ')' && code.charAt(i) != '{' && code.charAt(i) != ','
                                && code.charAt(i) != '}' && code.charAt(i) != '[' && code.charAt(i) != ']'
                                && code.charAt(i) != ';' && code.charAt(i) != '\n' && code.charAt(i) != '+'
                                && code.charAt(i) != '-' && code.charAt(i) != '*' && code.charAt(i) != '/'
                                && code.charAt(i) != 32 && code.charAt(i) != 34 && code.charAt(i) != 39
                                && code.charAt(i) != '.' && code.charAt(i) != ':' && code.charAt(i) != '='
                                && code.charAt(i) != '<' && code.charAt(i) != '>' && code.charAt(i) != '&'
                                && code.charAt(i) != '|' && code.charAt(i) != '!' && code.charAt(i) != '%' && code.charAt(i) != '@')
                                && i <= code.length() - 2) {
                            temp += code.charAt(i);
                            i++;
                        }
                        tokens.add(validate(temp, lineNo, code.charAt(i)));
                    } else {
                        tokens.add(validate(temp, lineNo, code.charAt(i)));
                    }
                }

                if (code.charAt(i) == '\'') {
                    temp = "";
                    if (code.charAt(i + 1) == '\\') {
                        for (j = 0; j < 4; j++) {
                            temp += code.charAt(i + j);
                        }
                        i += j - 1;
                        tokens.add(validate(temp, lineNo, code.charAt(i)));
                    } else {
                        for (j = 0; j < 3; j++) {
                            temp += code.charAt(i + j);
                        }
                        i += j - 1;
                        tokens.add(validate(temp, lineNo, code.charAt(i)));
                    }
                } else if (code.charAt(i) == '"') {
                    temp = "";
                    temp += code.charAt(i);
                    for (j = 1; (code.charAt(i + j) != '"' && (i + j) <= code.length() - 2); j++) {
                        if (code.charAt(i + j) == '\\') {
                            temp += "\\";
                            temp += code.charAt(i + j + 1);
                            j++;
                        } else {
                            if (code.charAt(i + j) == '\n') {
                                lineNo++;
                            }
                            temp += code.charAt(i + j);
                        }
                    }
                    i += j;
                    if (code.charAt(i) != '\n') {
                        temp += code.charAt(i);
                    }
                    tokens.add(validate(temp, lineNo, code.charAt(i)));
                } else if (code.charAt(i) == '+' || code.charAt(i) == '-' || code.charAt(i) == '*'
                        || code.charAt(i) == '/' || code.charAt(i) == '=' || code.charAt(i) == '&'
                        || code.charAt(i) == '|' || code.charAt(i) == '>' || code.charAt(i) == '<'
                        || code.charAt(i) == '!' || code.charAt(i) == '%') {
                    temp = "";
                    temp += code.charAt(i);
                    if (code.charAt(i + 1) == '=' && (code.charAt(i) == '+' || code.charAt(i) == '-'
                            || code.charAt(i) == '*' || code.charAt(i) == '/' || code.charAt(i) == '='
                            || code.charAt(i) == '%' || code.charAt(i) == '<' || code.charAt(i) == '>'
                            || code.charAt(i) == '!')) {
                        temp += code.charAt(i + 1);
                        i++;
                        tokens.add(operator(temp, lineNo));
                    } else if ((code.charAt(i + 1) == '+' && code.charAt(i) == '+') || (code.charAt(i + 1) == '-'
                            && code.charAt(i) == '-')) {
                        temp += code.charAt(i + 1);
                        i++;
                        tokens.add(operator(temp, lineNo));
                    } else if (code.charAt(i) == '&' || code.charAt(i) == '|') {
                        temp += code.charAt(i + 1);
                        i++;
                        tokens.add(operator(temp, lineNo));
                    } else if ((Character.isDigit(code.charAt(i + 1)) || code.charAt(i + 1) == '.') && (code.charAt(i) == '+' || code.charAt(i) == '-')) {
                        i++;
                        while ((code.charAt(i) != '(' && code.charAt(i) != ')' && code.charAt(i) != '{' && code.charAt(i) != ','
                                && code.charAt(i) != '}' && code.charAt(i) != '[' && code.charAt(i) != ']'
                                && code.charAt(i) != ';' && code.charAt(i) != '\n' && code.charAt(i) != '+'
                                && code.charAt(i) != '-' && code.charAt(i) != '*' && code.charAt(i) != '/'
                                && code.charAt(i) != 32 && code.charAt(i) != 34 && code.charAt(i) != 39
                                && code.charAt(i) != '.' && code.charAt(i) != ':' && code.charAt(i) != '='
                                && code.charAt(i) != '<' && code.charAt(i) != '>' && code.charAt(i) != '&'
                                && code.charAt(i) != '|' && code.charAt(i) != '!' && code.charAt(i) != '%' && code.charAt(i) != '@')
                                && i <= code.length() - 2) {
                            temp += code.charAt(i);
                            i++;
                        }
                        if (!"".equals(temp) && code.charAt(i) != '.') {
                            tokens.add(validate(temp, lineNo, code.charAt(i)));
                            i--;
                        } else if (code.charAt(i) == '.') {
                            temp += code.charAt(i);
                            i++;
                            while ((code.charAt(i) != '(' && code.charAt(i) != ')' && code.charAt(i) != '{' && code.charAt(i) != ','
                                    && code.charAt(i) != '}' && code.charAt(i) != '[' && code.charAt(i) != ']'
                                    && code.charAt(i) != ';' && code.charAt(i) != '\n' && code.charAt(i) != '+'
                                    && code.charAt(i) != '-' && code.charAt(i) != '*' && code.charAt(i) != '/'
                                    && code.charAt(i) != 32 && code.charAt(i) != 34 && code.charAt(i) != 39
                                    && code.charAt(i) != '.' && code.charAt(i) != ':' && code.charAt(i) != '='
                                    && code.charAt(i) != '<' && code.charAt(i) != '>' && code.charAt(i) != '&'
                                    && code.charAt(i) != '|' && code.charAt(i) != '!' && code.charAt(i) != '%'
                                    && code.charAt(i) != '@') && i <= code.length() - 2) {
                                temp += code.charAt(i);
                                i++;
                            }
                            tokens.add(validate(temp, lineNo, code.charAt(i)));
                            i--;
                        }
                    } else {
                        tokens.add(operator(temp, lineNo));
                    }
                } else if (code.charAt(i) == '@') {
                    while (code.charAt(i) != '\n') {
                        i++;
                    }
                    lineNo++;
                } else if (code.charAt(i) == '\n') {
                    lineNo++;
                }

                if (code.charAt(i) != '+' && code.charAt(i) != '-' && code.charAt(i) != '*' && code.charAt(i) != '/'
                        && code.charAt(i) != '=' && code.charAt(i) != '&' && code.charAt(i) != '|' && code.charAt(i) != '>'
                        && code.charAt(i) != '<' && code.charAt(i) != '!' && code.charAt(i) != '%' && code.charAt(i) != '"'
                        && code.charAt(i) != '\'' && code.charAt(i) != '\n' && code.charAt(i) != 32 && code.charAt(i) != '@'
                        && !Character.isLetterOrDigit(code.charAt(i))) {
                    tokens.add(punctuator(code.charAt(i), lineNo));
                }

                i++;
            }

            Token token = new Token();
            token.CP = "$";
            tokens.add(token);

            // For writing in file
            j = 0;
            while (j < tokens.size()) {
                bw.append("(" + tokens.get(j).CP + "," + tokens.get(j).VP + "," + tokens.get(j).Line_Number + ")");
                bw.append("\n");
                j++;
            }
            bw.close();

            // For checking lexical error token
            j = 0;
            while (j < tokens.size()) {
                if ("LEXICAL_ERROR".equals(tokens.get(j).CP)) {
                    isLexError = true;
                    break;
                }
                j++;
            }

            if (!isLexError) {
                new Syntax();
            } else {
                System.out.println("Lexical Error.");
            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    static boolean DFA_FloatConstant(String temp) {
        int i = 0, CS = 0, FS = 3;
        String str = temp;
        while (i < str.length()) {
            CS = TT_FloatConstant(CS, str.charAt(i));
            i++;
        }
        return CS == FS;
    }

    static int TT_FloatConstant(int CS, char str) {
        // {digit,.,+-}
        int t[][] = {{1, 2, 1}, {1, 2, 4}, {3, 4, 4}, {3, 4, 4}, {4, 4, 4}};
        if (Character.isDigit(str)) {
            return t[CS][0];
        } else if (str == '.') {
            return t[CS][1];
        } else if (str == '+' || str == '-') {
            return t[CS][2];
        } else {
            return 4;
        }
    }

    static boolean DFA_IntegerConstant(String temp) {
        int i = 0, CS = 0, FS = 2;
        String str = temp;
        while (i < str.length()) {
            CS = TT_IntegerConstant(CS, str.charAt(i));
            i++;
        }
        return CS == FS;
    }

    static int TT_IntegerConstant(int CS, char str) {
        // {digit,+-}
        int t[][] = {{2, 1}, {2, 3}, {2, 3}, {3, 3}};
        if (Character.isDigit(str)) {
            return t[CS][0];
        } else if (str == '+' || str == '-') {
            return t[CS][1];
        } else {
            return 3;
        }
    }

    static boolean DFA_Identifier(String temp) {
        int i = 0, CS = 0, FS = 3;
        String str = temp;
        while (i < str.length()) {
            CS = TT_Identifier(CS, str.charAt(i));
            i++;
        }
        flag = false;
        return CS == FS;
    }

    static int TT_Identifier(int CS, char str) {
        // {alpha,digit,ss,_}  
        int t[][] = {{4, 4, 1, 4}, {3, 4, 4, 4}, {3, 3, 4, 4}, {3, 3, 4, 2}, {4, 4, 4, 4}};
        if (Character.isLetter(str)) {
            return t[CS][0];
        } else if (Character.isDigit(str) && str != '4') {
            return t[CS][1];
        } else if (str == '#' || str == '$' || str == '4' || str == '~' || str == '?') {
            if (flag != true) {
                flag = true;
                return t[CS][2];
            } else {
                return t[CS][1];
            }
        } else if (str == '_') {
            return t[CS][3];
        } else {
            return 4;
        }
    }

    static boolean DFA_SimpleIdentifier(String temp) {
        int i = 0, CS = 0, FS = 2;
        String str = temp;
        while (i < str.length()) {
            CS = TT_SimpleIdentifier(CS, str.charAt(i));
            i++;
        }
        return CS == FS;
    }

    static int TT_SimpleIdentifier(int CS, char str) {
        // {alpha,digit,_}
        int t[][] = {{2, 3, 3}, {2, 2, 1}, {2, 2, 3}, {3, 3, 3}};
        if (Character.isLetter(str)) {
            return t[CS][0];
        } else if (Character.isDigit(str)) {
            return t[CS][1];
        } else if (str == '_') {
            return t[CS][2];
        } else {
            return 3;
        }
    }

    static boolean DFA_CharacterConstant(String temp) {
        int i = 0, CS = 0, FS = 4;
        String str = temp;
        while (i < str.length()) {
            CS = TT_CharacterConstant(CS, str.charAt(i));
            i++;
        }
        flag = false;
        return CS == FS;
    }

    static int TT_CharacterConstant(int CS, char str) {
        //{',c,es,/}
        int t[][] = {{1, 5, 5, 5}, {5, 3, 5, 2}, {3, 5, 3, 3}, {4, 5, 5, 5}, {5, 5, 5, 5}, {5, 5, 5, 5}};
        if (flag == false) {
            if (str == (char) 39) { // for '
                return t[CS][0];
            } else if (Character.isLetterOrDigit(str) || str == '"' || str == '~' || str == '!' || str == '@' || str == '#' || str == '$'
                    || str == '%' || str == '^' || str == '&' || str == '*' || str == '(' || str == ')' || str == '-' || str == '_' || str == '+' || str == '='
                    || str == '{' || str == '}' || str == '[' || str == ']' || str == '|' || str == ':' || str == ';' || str == ',' || str == '.' || str == '/' || str == '<'
                    || str == '>' || str == '?' || str == ' ') {
                return t[CS][1];
            } else if (str == '\\') {
                flag = true;
                return t[CS][3];
            } else {
                return 5;
            }
        } else if (str == 'n' || str == 't' || str == 'r' || str == 'b' || str == '\'' || str == '\\') {
            flag = false;
            return t[CS][2];
        } else {
            return 5;
        }
    }

    static boolean DFA_StringConstant(String temp) {
        int i = 0, CS = 0, FS = 4;
        String str = temp;
        while (i < str.length()) {
            CS = TT_StringConstant(CS, str.charAt(i));
            i++;
        }
        flag = false;
        return CS == FS;
    }

    static int TT_StringConstant(int CS, char str) {
        //{",c,es,\}
        int t[][] = {{1, 5, 5, 5}, {4, 3, 5, 2}, {3, 5, 3, 3}, {4, 1, 5, 2}, {5, 5, 5, 5}, {5, 5, 5, 5}};
        if (flag == false) {
            if (str == (char) 34) { // for "
                return t[CS][0];
            } else if (Character.isLetterOrDigit(str) || str == '\'' || str == '~' || str == '!' || str == '@' || str == '#' || str == '$'
                    || str == '%' || str == '^' || str == '&' || str == '*' || str == '(' || str == ')' || str == '-' || str == '_' || str == '+' || str == '='
                    || str == '{' || str == '}' || str == '[' || str == ']' || str == '|' || str == ':' || str == ';' || str == ',' || str == '.' || str == '/' || str == '<'
                    || str == '>' || str == '?' || str == ' ') {
                return t[CS][1];
            } else if (str == '\\') {
                flag = true;
                return t[CS][3];
            } else {
                return 5;
            }
        } else if (str == 'n' || str == 't' || str == 'r' || str == 'b' || str == '"' || str == '\\' || str == '\'') {
            flag = false;
            return t[CS][2];
        } else {
            return 5;
        }
    }

}
