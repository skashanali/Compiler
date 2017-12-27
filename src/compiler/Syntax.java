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
            System.out.println("Syntax parse successful.");
        } else {
            System.out.println(i);
            System.out.println("Unsuccessful.");
        }
    }

    public static boolean StartingPoint() {
        return func_cfg();
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
                            if (body_function()) {
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

    public static boolean struct_cfg() {
        if ("STRUCT".equals(lexicalTokens.get(i).CP)) {
            i++;
            if ("STRUCT_IDENTIFIER".equals(lexicalTokens.get(i).CP)) {
                i++;
                if ("{".equals(lexicalTokens.get(i).CP)) {
                    i++;
                    if (struct_dec()) {
                        if ("}".equals(lexicalTokens.get(i).CP)) {
                            i++;
                            if (";".equals(lexicalTokens.get(i).CP)) {
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
    }

    public static boolean struct_dec() {
        if (identifier()) {
            if (struct_dec_list()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean struct_dec_list() {
        if (";".equals(lexicalTokens.get(i).CP)) {
            i++;
            if ("}".equals(lexicalTokens.get(i).CP)) {
                return true;
            } else if (struct_dec()) {
                return true;
            } else {
                return false;
            }

        } else if (",".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (identifier()) {
                if (struct_dec_list()) {
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

    public static boolean func_list() {
        if (func_cfg() || struct_cfg()) {
            if (func_list()) {
                return true;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean func_cfg() { // for both function declaring and defining
        if (func_identifier()) {
            if ("(".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (func_arg()) {
                    if (")".equals(lexicalTokens.get(i).CP)) {
                        i++;
                        if ("{".equals(lexicalTokens.get(i).CP)) {
                            i++;
                            if (body_function()) {
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

    public static boolean for_loop_cfg() {
        if ("FOR".equals(lexicalTokens.get(i).CP)) {
            i++;
            if ("(".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (for_init()) {
                    if (";".equals(lexicalTokens.get(i).CP)) {
                        i++;
                        if (expression()) {
                            if (";".equals(lexicalTokens.get(i).CP)) {
                                i++;
                                if (inc_dec()) {
                                    if (")".equals(lexicalTokens.get(i).CP)) {
                                        i++;
                                        if (body_if_loops()) {
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

    public static boolean while_loop_cfg() {
        if ("WHILE".equals(lexicalTokens.get(i).CP)) {
            i++;
            if ("(".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (expression()) {
                    if (")".equals(lexicalTokens.get(i).CP)) {
                        i++;
                        if (body_if_loops()) {
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

    public static boolean body_if_loops() {
        if (single_statement()) {
            return true;
        } else if (";".equals(lexicalTokens.get(i).CP)) {
            i++;
            return true;
        } else if ("{".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (multi_statements()) {
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
    }

    public static boolean body_function() {
        if (multi_statements()) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean single_statement() {
        if (dec_with_init_cfg() || expression() || for_loop_cfg() || while_loop_cfg() || if_else_cfg() || dec_without_init_cfg()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean multi_statements() {
        if (single_statement()) {
            if (multi_statements()) {
                return true;
            } else {
                return false;
            }
        } else if ("{".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (multi_statements()) {
                if ("}".equals(lexicalTokens.get(i).CP)) {
                    i++;
                    if (multi_statements()) {
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
            return true;
        }
    }
//    public static boolean array(){
//
//    }

    public static boolean if_else_cfg() {
        if ("IF".equals(lexicalTokens.get(i).CP)) {
            i++;
            if ("(".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (expression()) {
                    if (")".equals(lexicalTokens.get(i).CP)) {
                        i++;
                        if (body_if_loops()) {
                            if (else_if()) {
                                if (and_else()) {
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

    public static boolean else_if() {
        if ("ELSE_IF".equals(lexicalTokens.get(i).CP)) {
            i++;
            if ("(".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (expression()) {
                    if (")".equals(lexicalTokens.get(i).CP)) {
                        i++;
                        if (body_if_loops()) {
                            if (else_if()) {
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
            return true;
        }
    }

    public static boolean and_else() {
        if ("ELSE".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (body_if_loops()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean func_call_cfg() {
        if (func_identifier()) {
            if ("(".equals(lexicalTokens.get(i).CP)) {
                i++;
                if (func_params()) {
                    if (")".equals(lexicalTokens.get(i).CP)) {
                        i++;
                        if (";".equals(lexicalTokens.get(i).CP)) {
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

    public static boolean func_params() {
        if (identifier() || constants()) {
            if (func_params_list()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean func_params_list() {
        if (",".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (identifier() || constants()) {
                if (func_params_list()) {
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

    public static boolean func_arg() {
        if (identifier()) {
            if (func_arg_list()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean func_arg_list() {
        if (",".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (identifier()) {
                if (func_arg_list()) {
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

    public static boolean dec_with_init_cfg() {
        if (identifier()) {
            if (dec_init()) {
                if (dec_list_with_init()) {
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

    public static boolean dec_list_with_init() {
        if (";".equals(lexicalTokens.get(i).CP)) {
            i++;
            return true;
        } else if (",".equals(lexicalTokens.get(i).CP)) {
            i++;
            if (identifier()) {
                if (dec_init()) {
                    if (dec_list_with_init()) {
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
            if (dec_init2()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean dec_init2() {
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

    public static boolean array_identifier() {
        if ("ARRAY_STRING_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "ARRAY_FLOAT_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "ARRAY_INTEGER_IDENTIFIER".equals(lexicalTokens.get(i).CP) || "ARRAY_BOOLEAN_IDENTIFIER".equals(lexicalTokens.get(i).CP)
                || "ARRAY_CHARACTER_IDENTIFIER".equals(lexicalTokens.get(i).CP)) {
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
        } else if (func_call_cfg()) {
            return true;
        } else {
            return false;
        }
    }

}
