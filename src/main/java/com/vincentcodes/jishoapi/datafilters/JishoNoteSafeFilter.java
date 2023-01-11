package com.vincentcodes.jishoapi.datafilters;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * If the project do go public, I may need to apply a filter
 * to hide my own secret (my notes may contain personal info
 * but idk where).
 */
@Component
@Qualifier("safe")
public class JishoNoteSafeFilter implements ModificationFilter<String>{
    // filter("2+d2\nasd\ndsa\n\n-d2\n12\nasd.\nmeaning. dsadsaasd\ndiff.dsadsa\nsim. dsadsads\nyoyo—dsa\nnono\nmomo mamamia — lala\n#finally\nasd. meaning\nnice. people\nmama — mia");
    /**
     * @return modified string result
     */
    public String filter(String input){
        StringBuilder sb = new StringBuilder();
        String[] lines = input.split("\n");
        if(lines.length == 0) return input;
        if(lines[0].matches("^-?[0-9](.*)")){
            sb.append(lines[0]).append('\n').append('\n');
        }
        boolean afterHeader = false;
        for(int i = 1; i < lines.length; i++){
            String line = lines[i];
            // skip it, don't want to go through the slow matching process
            if(line.isEmpty()) continue;
            if(line.startsWith("#") || line.startsWith("===") || line.startsWith("---"))
                afterHeader = true;
            if(line.contains("—") || (!afterHeader && line.matches("^[a-z]{1,10}\\.(.*)")))
                sb.append(line).append('\n');
        }
        return sb.toString();
    }
}
