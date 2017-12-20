//package filters;
//
//import twitter4j.Status;
//
//import java.util.List;

//public class AndFilter {
//}
package filters;

        import twitter4j.Status;

        import java.util.List;

/**
 * A filter that represents the logical not of its child filter
 */
public class AndFilter implements Filter {
    private final Filter child1;
    private final Filter child2;

    public AndFilter(Filter child1, Filter child2) {

        this.child1 = child1;
        this.child2 = child2;
    }

    /**
     * An and filter matches when its both its children does
     * @param s     the tweet to check
     * @return      whether or not it matches
     */
    @Override
    public boolean matches(Status s) {
        return child1.matches(s) && child2.matches(s);
    }

    @Override
    public List<String> terms() {
        return child1.terms();
    }

    public String toString() {
        return "(" + child1.toString() + " and " + child2.toString() + ")";
    }
}
