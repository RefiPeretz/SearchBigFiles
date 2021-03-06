import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Aggregator {

    static final int rowsCount = 1000;
    static final String path = "big.txt";
    public HashMap<String,ArrayList<Integer[]>> namesPositions = new HashMap<>();

    /**
     * Merging lists per name.
     * @param merge
     */
    private void mergeHashMaps(HashMap<String,ArrayList<Integer[]>> merge){
        ArrayList<Integer[]> curValue;
        for(String curKey : merge.keySet()){
            curValue = namesPositions.get(curKey);
           if(curValue != null){
               //Merge list
               curValue.addAll(merge.get(curKey));
               namesPositions.put(curKey, curValue);
           }
           else{
               namesPositions.put(curKey, merge.get(curKey));
           }
        }
    }

    /**
     *  Print names map according to format.
     */
    private void printMap(){
        String newLine;
        for(String curKey : namesPositions.keySet()){
            newLine = curKey + "--> [";
            ArrayList<Integer[]> value = namesPositions.get(curKey);
            for(Integer[] pos : value){
                newLine += "[lineOffset=" + pos[0] + ", charOffset=" + pos[1] + "],";
            }
            //Remove redundant ,
            newLine = newLine.substring(0, newLine.length() - 1);
            newLine += "]";
            System.out.println(newLine);

        }
    }

    /**
     * Run multithreaded search of top 50 names.
     * @throws Exception
     */
    public void runSearch() throws Exception{
        FileInputStream inputStream = null;
        Scanner sc = null;
        HashMap<String, ArrayList<Integer[]>> mergeTo;
        try {
            //Open file
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            int rowIndex = 0;
            //read until end of file
            while (sc.hasNextLine()) {
                int counter = 0;
                String matcherString = "";
                //read in chunks of 1000.
                while(counter < rowsCount && sc.hasNextLine()){
                    matcherString += sc.nextLine();
                    counter++;
                }
                System.out.println("Start on: "+ (rowIndex+ 1)*1000+ " chunk.");
                MatcherManager manager = new MatcherManager();
                mergeHashMaps(manager.searchNames(matcherString,rowIndex*1000));
                rowIndex++;
            }
            printMap();
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }

    }


    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String [ ] args) throws Exception
    {
        Aggregator arg = new Aggregator();
        arg.runSearch();

    }

}
