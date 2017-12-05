package wordgraph;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Test driver for WordGraph.
 * 
 * @author E. Stark
 * @version 20171124
 */
public class Test {
    
    /**
     * @param args An array of two words, with the starting word at index 0
     * and the goal word at index 1.
     */
    
    public static void main(String[] args) throws IOException {
        File file = new File("Data/words.txt");
        System.out.println(file.getCanonicalPath());
        Scanner scanner = new Scanner(new File("data/words.txt"));
        Iterator<Word> it = new Iterator<Word>() {
            @Override
            public boolean hasNext() {
                return scanner.hasNext();
            }
            
            @Override
            public Word next() {
                return new Word(scanner.next());
            }
        };
        System.out.print("Building word graph...");
        WordGraph wg = new WordGraph(it);
        System.out.println("done.");
        Word start = new Word(args[0]);
        if(!wg.containsNode(start)) {
            System.out.println("Starting word '" + start + "' is not in the word list.");
            System.exit(1);
        }
        Word goal = new Word(args[1]);
        if(!wg.containsNode(start)) {
            System.out.println("Ending word '" + goal + "' is not in the word list.");
            System.exit(1);
        }
        System.out.println("Searching for path from '" + start + "' to '" + goal + "'...");
        List<Word> path = wg.minimumWeightPath(start, goal);
        if(path == null) {
            System.out.println("No path found.");
        } else {
            for(Word word : path) {
                System.out.print(word + " ");
            }
            System.out.println();
        }
    }
    
}
