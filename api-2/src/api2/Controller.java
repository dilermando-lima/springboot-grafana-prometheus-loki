package api2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;

import java.util.Random;
import java.util.stream.Stream;

@RestController
public class Controller {

     private final Logger LOG = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping("/api2")
    public String anyLetter(@RequestHeader(name = "X-REQUEST-ID", required = false) String requestId) {

        org.slf4j.MDC.put("X-REQUEST-ID", requestId); // set request id to logback and loki

        LOG.info("random letter in api2");
        var letter = "" + ((char)(65 + new Random().nextInt(26)));

        meterRegistry.counter("request_" + letter).increment(); //  add new metric into prometheus

        if(Stream.of("A","E","G","S","Z","J").anyMatch(t -> t.equals(letter))){
        
            meterRegistry.counter("request_letter_error").increment(); //  add new metric into prometheus
            var exception = new RuntimeException("error on letter " + letter);
            LOG.error(exception.getMessage(), exception);
            throw exception;
        
        }else{
            
            meterRegistry.counter("request_letter_ok").increment(); //  add new metric into prometheus

        }

        return letter;
    }
    
}
