package util;

public class Range {
    private long low;
    private long high;

    public Range(String range) {
        String[] limits = range.split("-");
        this.low = Long.parseLong(limits[0]);
        this.high = Long.parseLong(limits[1]);
    }

    public boolean isInRange(long val) {
        return val >= low && val <= high;
    }

    public boolean mergeRange(Range other) {
        if (low <= other.low && high >= other.high) {
            return true;
        }
        if (low >= other.low && high <= other.high) {
            low = other.low;
            high = other.high;
            return true;
        }
        if (low <= other.low && high >= other.low) {
            high = other.high;
            return true;
        }
        if (low >= other.low && low <= other.high) {
            low = other.low;
            return true;
        }
        return false;
    }

    public long getCoverage() {
        return high - low + 1;
    }

    @Override
    public String toString() {
        return low + "-" + high;
    }
}
