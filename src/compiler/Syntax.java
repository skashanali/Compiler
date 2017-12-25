package compiler;

import java.util.*;

public class Syntax {

    static ArrayList<Token> lexicalTokens = new ArrayList<>();
    static int i;

    public Syntax() {
        while (i < Compiler.tokens.size()) {
            lexicalTokens.add(Compiler.tokens.get(i));
            i++;
        }
        printTokens();
        enterSyntax();
    }

    public void printTokens() {
        i = 0;
        while (i < lexicalTokens.size()) {
            System.out.println("(" + lexicalTokens.get(i).CP + "," + lexicalTokens.get(i).VP + "," + lexicalTokens.get(i).Line_Number + ")");
            i++;
        }
    }

    public static void enterSyntax() {
        i = 0;
        if (StartingPoint() == true && "$".equals(lexicalTokens.get(i).CP)) {
            System.out.println("woah woah woah");
        } else {
            System.out.println("gand marao");
        }
    }

    public static boolean StartingPoint() {
        if (func_list()) {
            if (main_cfg()) {
                if (func_list()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean main_cfg() {
        if ("VOID".equals(lexicalTokens.get(i).CP)) {
            i++;
            if ("MAIN".equals(lexicalTokens.get(i).CP)) {
                i++;
                if ("(".equals(lexicalTokens.get(i).CP)) {
                    i++;
                    if (")".equals(lexicalTokens.get(i).CP)) {
                        i++;
                        if ("{".equals(lexicalTokens.get(i).CP)) {
                            i++;
                            if (dec_with_init_cfg()) {

                                if ("}".equals(lexicalTokens.get(i).CP)) {
                                    i++;
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean func_list() {
        if (func_cfg()) {
            if (func_list()) {
                return true;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean func_cfg() {
        if (func_identifier()) {
            if ("(".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (dec_without_init_cfg() || ")".equals(lexicalTokens.get(i).CP)) {
                    if (")".equals(lexicalTokens.get(i).CP)) {
                        i++;
                        if ("{".equals(lexicalTokens.get(i).CP)) {
                            i++;
                            if (dec_with_init_cfg() || "}".equals(lexicalTokens.get(i).CP)) {

                                if ("}".equals(lexicalTokens.get(i).CP)) {
                                    i++;
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean dec_without_init_cfg() {
        if (identifier()) {
            if (list_without_init()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean list_without_init() {
        if (";".equals(lexicalTokens.get(i).CP)) {
            i++;
            return true;
        } else if (",".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (identifier()) {
                if (list_without_init()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean dec_with_init_cfg() {
        if (identifier()) {
            if (init()) {
                if (list_with_init()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean list_with_init() {
        if (";".equals(lexicalTokens.get(i).CP)) {
            i++;
            return true;
        } else if (",".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (identifier()) {
                if (init()) {
                    if (list_with_init()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean init() {
        if ("ASSIGNMENT_OPERATOR".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (init2()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean init2() {
        if (identifier()) {
            if (init()) {
                return true;
            } else {
                return false;
            }
        } else if (constants()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean constants() {
        if ("INTEGER_CONSTANT".equals(lexicalTokens.get(i).CP) || "CHARACTER_CONSTANT".equals(lexicalTokens.get(i).CP)
                || "FLOAT_CONSTANT".equals(lexicalTokens.get(i).CP) || "STRING_CONSTANT".equals(lexicalTokens.get(i).CP)
                || "BOOLEAN_CONSTANT".equals(lexicalTokens.get(i).CP)) {
            i++;
            return true;
        } else {
            return false;
        }
    }

    public static boolean identifier() {
        if ("STRING_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "FLOAT_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "INTEGER_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "BOOLEAN_IDENTIFIER".equals(lexicalTokens.get(i).CP)
                || "CHARACTER_IDENTIFIER".equals(lexicalTokens.get(i).CP)) {
            i++;
            return true;
        } else {
            return false;
        }
    }

    public static boolean func_identifier() {
        if ("FUNCTION_STRING_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "FUNCTION_FLOAT_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "FUNCTION_INTEGER_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "FUNCTION_BOOLEAN_IDENTIFIER".equals(lexicalTokens.get(i).CP)
                || "FUNCTION_CHARACTER_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "FUNCTION_VOID_IDENTIFIER".equals(lexicalTokens.get(i).CP)) {
            i++;
            return true;
        } else {
            return false;
        }
    }

}
