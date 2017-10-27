import java.util.ArrayList;

/**
 * Matcher class. Implement multithreaded search using RabinKarp search.
 *
 */
public class Matcher implements Runnable {

    private String name;
    public Thread t;
    private String threadName;
    private ArrayList<Integer[]> positions;
    private ArrayList<Integer> charPos;
    private boolean isExtract = false;
//    String currentRow;
    int currentRow;
    String text;

    /**
     * Constructor
     * @param name
     * @param currentRow
     * @param text
     */
    public Matcher(String name, int currentRow, String text){
        this.name = name;
        this.threadName = name + "_thread";
        this.text = text;
        this.currentRow = currentRow;
    }

    /**
     * Is the thread is done and the map was extracted.
     * @return
     */
    public boolean isExtract(){
        return this.isExtract;
    }

    /**
     * Set extract true or false.
     * @param value
     */
    public void setIsExtract(boolean value){
        this.isExtract = value;
    }

    public ArrayList<Integer[]> getPositions() {
        return this.positions;
    }

    public String getName(){
        return this.name;
    }

    public void run() {
        //System.out.println("Running " +  threadName );
        try {
            RabinKarp searcher = new RabinKarp(this.name);
            //List of positions
            charPos = searcher.search(this.text);
            if(!charPos.isEmpty()){
                for(int pos : charPos){
                    Integer[] temp = {currentRow,pos};
                    positions.add(temp);
                }
            }
        }catch (Exception e) {
            //System.out.println("Thread " +  threadName + " interrupted.");
        }
       // System.out.println("Thread " +  threadName + " exiting.");
    }
    //Start thread
    public void start () {
        positions = new ArrayList<>();
        //System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}


