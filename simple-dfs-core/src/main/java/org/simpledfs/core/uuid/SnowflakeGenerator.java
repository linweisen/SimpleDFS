package org.simpledfs.core.uuid;


/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/14
 **/
public class SnowflakeGenerator implements UUIDGenerator {

    private final long tEpoch = System.currentTimeMillis();

    private final int nodeDigits = 7;

    private final int datacenterDigits = 3;

    private final int sequenceDigits = 12;

    private final int nodeShift = sequenceDigits;

    private final int datacenterShift = sequenceDigits + nodeDigits;

    private final int timestampLeftShift = sequenceDigits + nodeDigits + datacenterDigits;

    private final int sequenceMask = ~(-1 << sequenceDigits);

    private final int node;

    private final int datacenter;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    public SnowflakeGenerator(int node, int datacenter) {
        int maxNode = ~(-1 << nodeDigits);
        if (node > maxNode || node < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", node));
        }
        int maxDatacenter = ~(-1 << datacenterDigits);
        if (datacenter > maxDatacenter || datacenter < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", datacenter));
        }
        this.node = node;
        this.datacenter = datacenter;
    }

    @Override
    public long getUID() {
        return nextId();
    }


    public synchronized long nextId() {
        long timestamp = currentTime();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        }else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        return ((timestamp - tEpoch) << timestampLeftShift)
                | (datacenter << datacenterShift)
                | (node << nodeShift)
                | sequence;

    }

    protected long currentTime() {
        return System.currentTimeMillis();
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }
}
