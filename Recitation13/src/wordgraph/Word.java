package wordgraph;

import java.util.Objects;
import java.util.Random;

/**
 * Objects of this class represent dictionary words.
 * 
 * @author E. Stark
 * @version 20171124
 */
public class Word implements Comparable<Word> {
    
    /** The text of the word. */
    private final String text;
    
    /**
     * Initialize a word with specified text.
     *
     * @param text the text of the word.
     */
    public Word(String text) {
        this.text = text;
    }
    
    /**
     * Get the text of this word.
     * 
     * @return the text of this word.
     */
    public String getText() {
        return text;
    }
    
    /**
     * Get the length of this word.
     * 
     * @return the length of this word.
     */
    public int length() {
        return text.length();
    }
    
    /**
     * Obtain a word derived from this word by deleting the letter at
     * a specified index.
     *
     * @param i The index of the letter to be deleted.
     * @return a word derived from this word by deleting the letter at
     * index i, where 0 <= i < length()
     */
    public Word deleteLetter(int i , int x) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        char c = characters.charAt(x);
        if(i == 0){
            return new Word(c + text.substring(1));
        }    
        else if(i == text.length() - 1)
            return new Word(text.substring(0, i) + c);
        else
            return new Word(text.substring(0, i) + c + text.substring (i+1));
    }
    
    @Override
    public int compareTo(Word word) {
        return text.compareTo(word.text);
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == null)
            return false;
        if(other == this)
            return true;
        if(other.getClass() != getClass())
            return false;
        Word ow = (Word)other;
        return text.equals(ow.text);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.text);
        return hash;
    }
    
    @Override
    public String toString() {
        return text;
    }
    
}
