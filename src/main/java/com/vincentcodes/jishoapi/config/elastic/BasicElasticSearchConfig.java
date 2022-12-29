package com.vincentcodes.jishoapi.config.elastic;

import java.util.Arrays;

/**
 * For @ConfigurationProperties to work, you need public setters with correct names
 */
public class BasicElasticSearchConfig {
    public String indexPath;

    public String indexName;

    public String[] analyzers;

    // Getters and Setters
    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String[] getAnalyzers() {
        return analyzers;
    }

    public void setAnalyzers(String[] analyzers) {
        this.analyzers = analyzers;
    }

    public String normalAnalyzerName(){
        if(analyzers == null)
            return null;
        return analyzers[0];
    }
    public String noCompoundAnalyzerName(){
        if(analyzers == null)
            return null;
        if(analyzers.length < 2)
            return null;
        return analyzers[1];
    }

    @Override
    public String toString() {
        return "JishoNotesConfig{" +
                "indexPath='" + indexPath + '\'' +
                ", indexName='" + indexName + '\'' +
                ", analyzers=" + Arrays.toString(analyzers) +
                '}';
    }
}
