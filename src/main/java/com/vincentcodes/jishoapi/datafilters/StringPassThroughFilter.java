package com.vincentcodes.jishoapi.datafilters;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * If the project do go public, I may need to apply a filter
 * to hide my own secret (my notes may contain personal info
 * but idk where).
 */
@Component
@Qualifier("default")
public class StringPassThroughFilter implements ModificationFilter<String>{
    public String filter(String input){
        return input;
    }
}
