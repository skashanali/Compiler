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
            System.out.println(i);
            System.out.println("woah woah woah");
        } else {
            System.out.println(i);
            System.out.println("gand marao");
        }
    }

    public static boolean StartingPoint() {
        return condition();
//        if (func_list()) {
//            if (main_cfg()) {
//                if (func_list()) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
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
                        } else if (";".equals(lexicalTokens.get(i).CP)) {
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
    }

    public static boolean for_loop() {
        if ("FOR".equals(lexicalTokens.get(i).CP)) {
            i++;
            if ("(".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (for_init()) {
                    if (";".equals(lexicalTokens.get(i).CP)) {
                        i++;
                        if (condition()) {
                            if (";".equals(lexicalTokens.get(i).CP)) {
                                i++;
                                if (inc_dec()) {
                                    if (")".equals(lexicalTokens.get(i).CP)) {
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
        } else {
            return false;
        }
    }

    public static boolean for_init() {
        if (identifier()) {
            if ("ASSIGNMENT_OPERATOR".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (identifier()) {
                    return true;
                } else if (constants()) {
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean inc_dec() {
        if ("INC_DEC".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (identifier()) {
                return true;
            } else {
                return false;
            }
        } else if (identifier()) {
            if ("INC_DEC".equals(lexicalTokens.get(i).CP)) {
                i++;
                return true;
            } else if ("ASSIGNMENT_OPERATOR".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (identifier()) {
                    return true;
                } else if (constants()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean condition() {
        if (expression()) {
            if ("RELATIONAL_OPERATOR".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (expression()) {
                    System.out.println(lexicalTokens.get(i).CP);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
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
            if (dec_init()) {
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
                if (dec_init()) {
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

    public static boolean dec_init() {
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
            if (dec_init()) {
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

    public static boolean expression() {
        if (or_expression()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean or_expression() {
        if (and_expression()) {
            if (or_expression1()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean or_expression1() {
        if ("OR".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (or_expression()) {
                if (or_expression1()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean and_expression() {
        if (relational_expression()) {
            if (and_expression1()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean and_expression1() {
        if ("AND".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (and_expression()) {
                if (and_expression1()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean relational_expression() {
        if (add_sub_expression()) {
            if (relational_expression1()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean relational_expression1() {
        if ("RELATIONAL_OPERATOR".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (add_sub_expression()) {
                if (relational_expression1()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean add_sub_expression() {
        if (mul_div_expression()) {
            if (add_sub_expression1()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean add_sub_expression1() {
        if ("ADD_SUB".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (mul_div_expression()) {
                if (add_sub_expression1()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean mul_div_expression() {
        if (final_expression()) {
            if (mul_div_expression1()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean mul_div_expression1() {
        if ("MUL_DIV".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (final_expression()) {
                if (mul_div_expression1()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean final_expression() {
        if (identifier()) {
            if ("INC_DEC".equals(lexicalTokens.get(i).CP)) {
                i++;
                return true;
            } else {
                return true;
            }
        } else if (constants()) {
            return true;
        } else if ("(".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (expression()) {
                if (")".equals(lexicalTokens.get(i).CP)) {
//                    System.out.println(lexicalTokens.get(i).CP);
                    i++;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if ("INC_DEC".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (identifier()) {
                return true;
            } else {
                return false;
            }
        } else if ("UNARY_OPERATOR".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (expression()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
