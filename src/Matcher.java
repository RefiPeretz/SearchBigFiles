import java.util.ArrayList;

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

    public Matcher(String name, int currentRow, String text){
        this.name = name;
        this.threadName = name + "_thread";
//        this.currentRow =Integer.toString(currentRow);
        this.text = text;
        this.currentRow = currentRow;
    }
    public boolean isExtract(){
        return this.isExtract;
    }
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

    public void start () {
        positions = new ArrayList<>();
        //System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}


