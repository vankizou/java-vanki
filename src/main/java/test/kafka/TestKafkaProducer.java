package test.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;

/**
 * Created by vanki on 2019-02-13 14:47.
 */
public class TestKafkaProducer {
    public static void main(String[] args) {
        KafkaProducer producer = new KafkaProducer<String, String>(new Properties() {{
//            this.put("bootstrap.servers", "10.211.55.16:9092,10.211.55.17:9092,10.211.55.18:9092");
            this.put("bootstrap.servers", "54.222.139.120:9092");
            this.put("acks", "all");
            this.put("retries", 0);
            this.put("batch.size", 16384);
            this.put("linger.ms", 1);
            this.put("buffer.member", 33554432);
        }}, new StringSerializer(), new StringSerializer());

        Scanner scanner = new Scanner(System.in);

        try {
            while (scanner.hasNext()) {
                String next = scanner.next();
                if ("exit".equals(next)) {
                    break;
                } else {
                    producer.send(new ProducerRecord("test_topic", next));
                    System.out.println(String.format("发送消息：%s", next));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
            System.out.println("exit！");
        }
    }
}
