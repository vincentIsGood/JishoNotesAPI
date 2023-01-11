package com.vincentcodes.jishoapi.helpers;

public class JishoSearchExpressionProcessor {
    /**
     * whether the content matches the expression (it
     * can only contains && for 'and' and || for 'or')
     * @param content
     * @param expr
     * @return
     */
    public static boolean matches(String content, String expr){
        if(expr.contains("&&")){
            String[] splited = expr.split("&&");
            for(String subexpr : splited){
                subexpr = subexpr.trim();
                if(subexpr.contains("||")){
                    if(!matches(content, subexpr))
                        return false;
                }else if(!content.contains(subexpr))
                    return false;
            }
            return true;
        }else if(expr.contains("||")){
            String[] splited = expr.split("\\|\\|");
            for(String word : splited){
                word = word.trim();
                if(content.contains(word)){
                    return true;
                }
            }
            return false;
        }
        return content.contains(expr);
    }
}
