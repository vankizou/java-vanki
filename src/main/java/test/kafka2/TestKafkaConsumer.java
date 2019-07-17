package test.kafka2;

import java.util.Properties;

/**
 * Created by vanki on 2019-02-13 14:47.
 */
public class TestKafkaConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("zookeeper.connect", "10.211.55.16:2181");
        props.put("group.id", "g1");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");

    }
}
