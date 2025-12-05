package util;

public class Range {
    private long low;
    private long high;

    public Range(String range) {
        int index = range.indexOf('-');
        low = Long.parseLong(range.substring(0, index));
        high = Long.parseLong(range.substring(index + 1));
    }

    public boolean isInRange(long val) {
        return val >= low && val <= high;
    }

    public boolean mergeRange(Range other) {
        if (high + 1 < other.low || low - 1 > other.high) {
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
