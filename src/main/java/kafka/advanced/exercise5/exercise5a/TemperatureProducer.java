package kafka.advanced.exercise5.exercise5a;

import kafka.advanced.exercise5.exercise5a.model.Temperature;
import kafka.advanced.exercise5.exercise5a.model.Room;
import kafka.advanced.exercise5.exercise5a.serialization.RoomSerializer;
import kafka.advanced.exercise5.exercise5a.serialization.TemperatureSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;


public class TemperatureProducer {
    public void createProducer() {

        Random random = new Random(1);

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, RoomSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TemperatureSerializer.class.getName());

        // TODO: Create a KafkaProducer

        int ts = 0;
        try (KafkaProducer<Room, Temperature> termometer = new KafkaProducer<>(props)) {

            while (true) {

                Room key = new Room("room" + random.nextInt(5));

                int temperature = random.nextInt(40);

//                if (random.nextBoolean())
//                    temperature = -1 * temperature;

                Temperature value = new Temperature(temperature, ts += 2000);

                ProducerRecord<Room, Temperature> record =
                        new ProducerRecord<>("temperature", key, value);

                termometer.send(record);

                Thread.sleep(10000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TemperatureProducer helloProducer = new TemperatureProducer();
        helloProducer.createProducer();
    }
}
