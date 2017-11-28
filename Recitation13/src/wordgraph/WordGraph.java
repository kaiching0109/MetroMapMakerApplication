/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgraph;

import graph.AbstractEdgeWeightedGraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Objects of this class represent graphs whose nodes are words and where
 * the accessibility relation between words corresponds to insertion or
 * deletion of a letter.
 * 
 * @author E. Stark
 * @version 20171124
 */
public class WordGraph extends AbstractEdgeWeightedGraph<Word> {
    
    /**
     * Set of all the words in this WordGraph.
     */
    private final HashSet<Word> wordSet;
       
    /**
     * Map taking a word to a set of all the words accessible from it by
     * inserting or deleting a single letter.
     */
    private final HashMap<Word, Set<Word>> wordMap;
    
    /**
     * Initialize a WordGraph given an iterator to produce all the words.
     * 
     * @param words a collection of all the words.
     */
    public WordGraph(Iterator<Word> words) {
        wordSet = new HashSet<>();
        wordMap = new HashMap<>();
        String characters = "abcdefghijklmnopqrstuvwxyz";
        while(words.hasNext())
            wordSet.add(words.next());
        for(Word word : wordSet) {
            for(int i = 0; i < word.length(); i++) {
                for(int x = 0; x < characters.length(); x++){
                    Word w = word.deleteLetter(i, x);
                    if(wordSet.contains(w)) {
                        addToMap(word, w);
                        addToMap(w, word);
                    }
                }    
            }
        }
    }
    
    /**
     * Insert a pair of words in the map.
     * 
     * @param from The word to be used as the key.
     * @param to The word to be added to the list of words associated
     * with the key.
     */
    private void addToMap(Word from, Word to) {
        Set<Word> s = wordMap.get(from);
        if(s == null) {
            s = new HashSet<>();
            wordMap.put(from, s);
        }
        s.add(to);
    }
    
    @Override
    public boolean containsNode(Word word) {
        return wordSet.contains(word);
    }
    
    @Override
    public boolean isAdjacent(Word from, Word to) {
        Set<Word> s = wordMap.get(from);
        if(s == null)
            return false;
        else
            return s.contains(to);
    }

    @Override
    public Iterator<Word> nodeIterator(Word from) {
        if(from == null)
            return(wordSet.iterator());
        else {
            Set<Word> l = wordMap.get(from);
            if(l == null)
                l = new HashSet<>();
            return l.iterator();
        }
    }
    
    @Override
    public double edgeWeight(Word from, Word to) {
        if(isAdjacent(from, to))
            return 1;
        else
            return Double.POSITIVE_INFINITY;
    }

}
