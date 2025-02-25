package api1;

import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final Logger LOG = LoggerFactory.getLogger(Controller.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/api1")
    public String consumeApi2(@RequestHeader(name = "X-REQUEST-ID", required = false) String requestId) {

        org.slf4j.MDC.put("X-REQUEST-ID", requestId); // set request id to logback and loki
        
        LOG.info("calling api 2");
        String providerUrl = "http://localhost:8084/api2";


        HttpHeaders headers = new HttpHeaders();
        headers.set("X-REQUEST-ID", requestId);

        return restTemplate.exchange(providerUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class).getBody();
    }
    
}
