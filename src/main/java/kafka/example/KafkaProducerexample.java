package kafka.example;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaProducerexample {
    static String topicName = "quickstart-events";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        syncProducer();

        asyncProducer();
    }

    private static void asyncProducer() throws InterruptedException {

        System.out.println("Executing asynchronous Kafka producer");

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");

        Producer<String, String> producer = new KafkaProducer<>(properties);
//We create a CountDownLatch object with a count of 5 .
// This is used to wait until all messages have been sent before closing the producer.
//        CountDownLatch counter = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {

            String key = "async-key-" + i;
            String value = "async-value-" + i;

            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);

            producer.send(record, new Callback() {

                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        System.out.println("Error sending data " + key + e.getMessage());
                    } else {
                        System.out.println("Produced data: " + key + "=" + value);
                    }
//                    counter.countDown();
                }

            });

            Thread.sleep(1000);
        }
//We call the await method on the CountDownLatch object to wait until all messages have been sent and acknowledged by the broker.
//        counter.await();
//We close the KafkaProducer instance.
        producer.flush();
        producer.close();

        System.out.println("End of program");
    }

    private static void syncProducer() throws InterruptedException, ExecutionException {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");

        Producer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 5; i++) {

            String key = "sync-key-" + i;
            String value = "sync-value-" + i;

            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);

            Future<RecordMetadata> response = producer.send(record);
            response.get();

            System.out.println("Produced data: " + key + "=" + value);

            Thread.sleep(1000);
        }
        producer.flush();
        producer.close();

        System.out.println("End of program");
    }
}
