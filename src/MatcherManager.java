import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manage matcher for each name.
 */
public class MatcherManager {
    private static final String names[] = {"James","John","Robert","Michael","William","David",
            "Richard","Charles","Joseph","Thomas",
            "Christopher","Daniel","Paul","Mark","Donald","George",
            "Kenneth","Steven","Edward","Brian","Ronald","Anthony","Kevin","Jason","Matthew",
            "Gary","Timothy","Jose","Larry","Jeffrey",
            "Frank","Scott","Eric","Stephen","Andrew","Raymond","Gregory","Joshua","Jerry",
            "Dennis","Walter","Patrick","Peter","Harold","Douglas","Henry","Carl","Arthur","Ryan","Roger"};

    private final int namesLen = 50;

    /**
     * Constructor
     */
    public MatcherManager(){

    }

    /**
     * Create mather for each name and activate all.
     * @param text
     * @param currentRow
     * @return
     */
    public HashMap<String,ArrayList<Integer[]>> searchNames(String text,int currentRow){
        //Hold matches map of current chunk
        HashMap<String,ArrayList<Integer[]>> matchesMap = new HashMap<>();
        ArrayList<Matcher> matcherList = new ArrayList<>();
        //Run on all names.
        for(String name : names){
            Matcher newMatcher = new Matcher(name,currentRow,text);
            //start thread
            newMatcher.start();
            matcherList.add(newMatcher);
        }
        //Polling
        int threadsCompleted = 0;
        while(threadsCompleted < namesLen){
            for(Matcher matcher: matcherList){
                if(!matcher.isExtract() && !matcher.t.isAlive()){
                    if(!matcher.getPositions().isEmpty()){
                        matchesMap.put(matcher.getName(),matcher.getPositions());
                    }
                    matcher.setIsExtract(true);
                    threadsCompleted++;
                }
            }
        }
        return matchesMap;
    }
}

