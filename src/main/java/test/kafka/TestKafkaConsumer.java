package test.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by vanki on 2019-02-13 14:47.
 */
public class TestKafkaConsumer {

    public static void main(String[] args) {
        KafkaConsumer consumer = new KafkaConsumer(new Properties() {{
//            this.put("bootstrap.servers", "10.211.55.16:9092,10.211.55.17:9092,10.211.55.18:9092");
            this.put("bootstrap.servers", "54.222.139.120:9092");
            this.put("group.id", "g1");
            //如果value合法，则自动提交偏移量
            this.put("enable.auto.commit", "true");
            //设置多久一次更新被消费消息的偏移量
            this.put("auto.commit.interval.ms", "1000");
            //设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
            this.put("session.timeout.ms", "30000");
            //自动重置offset
            this.put("auto.offset.reset", "earliest");
        }}, new StringDeserializer(), new StringDeserializer());

        consumer.subscribe(Arrays.asList("test_topic"));
        System.out.println("start...");

        for (; ; ) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            System.out.println(records.count());

            records.forEach(
                    r -> {
                        System.out.println(String.format("offset=%s, value=%s, count=%s", r.offset(), r.value(), records.count()));

                        // 如果 enable.auto.commit=false，则用下面手动提交
                        /*consumer.commitSync(new HashMap<TopicPartition, OffsetAndMetadata>(1) {{
                            this.put(new TopicPartition(r.topic(), r.partition()), new OffsetAndMetadata(r.offset()));
                        }});*/
                    }
            );
        }
        /*Properties props = new Properties();
//        props.put("zookeeper.connect", "10.211.55.16:2181,10.211.55.17:2181,10.211.55.18:2181");
        props.put("zookeeper.connect", "54.222.139.120:2182");
        props.put("group.id", "g1");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");

        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer.createMessageStreams(new HashMap<String, Integer>(1) {{
            this.put("test_topic", 2);
        }});

        System.out.println("start...");

        List<KafkaStream<byte[], byte[]>> topics = streamMap.get("test_topic");
        System.out.println(topics.size());

        for (KafkaStream<byte[], byte[]> stream : topics) {
            System.out.println("stream: " + stream.size());

            ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
            while (iterator.hasNext()) {
                MessageAndMetadata<byte[], byte[]> next = iterator.next();
                System.out.println(String.format("msg: %s, topic: %s, partition: %s, offset: %s", new String(next.message()), next.topic(), next.partition(), next.offset()));
            }
        }*/
    }
}
