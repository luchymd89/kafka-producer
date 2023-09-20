import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    private static final Logger log = LogManager.getLogger(App.class);

    public static void main(String[] args){

        Producer kafkaProducer = Producer.getInstance();

        Scanner scanner = new Scanner(System.in);

        do{
            String line = scanner.next();

            //Entrada se espera que sea del tipo clave:valor
            Boolean isValidLine = validFormat(line);

            if (isValidLine || line.equals("exit")){
                if (line.equals("exit")){
                    log.info("Finish");
                    kafkaProducer.close();
                    break;
                }

                String[] keyAndMessage = line.split(":");
                kafkaProducer.send(keyAndMessage[0], keyAndMessage[1]);

            }else {
                log.error("The format must be string:string");
            }



        }while(true);

    }


    private static Boolean validFormat(String line){
        Pattern regex = Pattern.compile("\\w+:\\w+"); // Valida el tipo Palabra:Palabra
        Matcher matcher = regex.matcher(line);

        return matcher.matches();

    }

}
