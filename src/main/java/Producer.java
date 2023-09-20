import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Producer {

    private static final Logger log = LogManager.getLogger(Producer.class);
    private static Producer producer;

    //Lo que va a permitir enviar los mensajes, como en el properties key y value son String, se le pasa Strings .
    private KafkaProducer<String, String> kafkaProducer;


    private static final String TOPIC = "primer-topic";
    private static final Integer PARTITION = 0;

    //Producer va a ser un singleton
    private Producer(){

        try {
            Properties conf = new Properties();
            // Se le pasa el lugar donde estan las propiedades
            conf.load(new FileReader("src/main/resources/producer.properties"));

            this.kafkaProducer = new KafkaProducer<String, String>(conf);


        }catch (IOException ioe){
            log.error(ioe.getMessage());

        }

    }

    public static Producer getInstance(){

        if (producer == null){
            producer = new Producer();
        }


        return producer;

    }

    public void send(String key, String value){

        try{

            ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, PARTITION, key, value);
            this.kafkaProducer.send(record);

        }catch (KafkaException e){
            log.error(e.getMessage());
            this.close();
        }

    }

    public void close(){
        this.kafkaProducer.close();
    }
}
