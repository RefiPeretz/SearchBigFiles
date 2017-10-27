
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 *  This class implement RabinKarp search algorithm with little modification to multiple occurrences search
 */
public class RabinKarp {
    private String pat;      // the pattern  // needed only for Las Vegas
    private long patHash;    // pattern hash value
    private int m;           // pattern length
    private long q;          // a large prime, small enough to avoid long overflow
    private int R;           // radix
    private long RM;         // R^(M-1) % Q
    private ArrayList<Integer> nameIndexList = new ArrayList<Integer>();;

    /**
     * Preprocesses the pattern string.
     *
     * @param pattern the pattern string
     * @param R       the alphabet size
     */
    public RabinKarp(char[] pattern, int R) {
        this.pat = String.valueOf(pattern);
        this.R = R;
        throw new UnsupportedOperationException("Operation not supported yet");
    }

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public RabinKarp(String pat) {
        this.pat = pat;      // save pattern (needed only for Las Vegas)
        R = 256;
        m = pat.length();
        q = longRandomPrime();

        // precompute R^(m-1) % q for use in removing leading digit
        RM = 1;
        for (int i = 1; i <= m - 1; i++)
            RM = (R * RM) % q;
        patHash = hash(pat, m);
    }

    /**
     * Compute hash for key[0..m-1].
     * @param key
     * @param m
     * @return
     */
    private long hash(String key, int m) {
        long h = 0;
        for (int j = 0; j < m; j++)
            h = (R * h + key.charAt(j)) % q;
        return h;
    }

    /**
     *  Does pat[] match txt[i..i-m+1]
     * @param txt
     * @param i
     * @return
     */
    private boolean check(String txt, int i) {
        for (int j = 0; j < m; j++)
            if (pat.charAt(j) != txt.charAt(i + j))
                return false;
        return true;
    }



    /**
     * Returns Array of occurrences positions
     * in the text string.
     *
     * @param txt the text string
     * @return Returns Array of occurrences positions
     */
    public ArrayList<Integer> search(String txt) {
        int n = txt.length();
        if (n < m) return nameIndexList;
        long txtHash = hash(txt, m);

        // check for match at offset 0
        if ((patHash == txtHash) && check(txt, 0))
            nameIndexList.add(0);

        // check for hash match; if hash match, check for exact match
        for (int i = m; i < n; i++) {
            // Remove leading digit, add trailing digit, check for match. 
            txtHash = (txtHash + q - RM * txt.charAt(i - m) % q) % q;
            txtHash = (txtHash * R + txt.charAt(i)) % q;

            // match
            int offset = i - m + 1;
            if ((patHash == txtHash) && check(txt, offset)){
                nameIndexList.add(offset);
            }

        }

        // no match - return empty
        return nameIndexList;
    }


    /**
     * a random 31-bit prime
     * @return
     */
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

}