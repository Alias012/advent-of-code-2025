package util;

public class Range {
    private long low;
    private long high;

    public Range(String range) {
        String[] limits = range.split("-");
        low = Long.parseLong(limits[0]);
        high = Long.parseLong(limits[1]);
    }

    public boolean isInRange(long val) {
        return val >= low && val <= high;
    }

    public boolean mergeRange(Range other) {
        if (high < other.low || low > other.high) {
            return false;
        }
        low = Math.min(low, other.low);
        high = Math.max(high, other.high);
        return true;
    }

    public long getCoverage() {
        return high - low + 1;
    }

    @Override
    public String toString() {
        return low + "-" + high;
    }
}
