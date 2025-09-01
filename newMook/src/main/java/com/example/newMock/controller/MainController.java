package com.example.newMock.controller;

import com.example.newMock.model.RequestGTO;
import com.example.newMock.model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.EvalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();


    @PostMapping(
        value = "/info/postBalances",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    //(аннотация, название класса, экземпляр класса)
    public Object postBalances(@RequestBody RequestGTO requestGTO){
        try{

            String clientID = requestGTO.getClientId();
            char firstChar = clientID.charAt(0);    //первый символ
            BigDecimal maxLimit;
            String currency;    //валюта
            Random rand = new Random();
            BigDecimal balance;

            //выставление лимитов в зависимости от валюты
            if(firstChar == '8'){    //доллар
                maxLimit = new BigDecimal(2000);
                currency = "US";
                // рандомная генерация баланса от 0 до maxLimit
                balance = BigDecimal.valueOf(rand.nextDouble() * maxLimit.doubleValue()).setScale(2, RoundingMode.HALF_UP);
            }
            else if(firstChar == '9'){   //евро
                maxLimit = new BigDecimal(1000);
                currency = "EU";
                // рандомная генерация баланса от 0 до maxLimit
                balance = BigDecimal.valueOf(rand.nextDouble() * maxLimit.doubleValue()).setScale(2, RoundingMode.HALF_UP);
            }
            else{   // рубли
                maxLimit = new BigDecimal(10000);
                currency = "RUB";
                // рандомная генерация баланса от 0 до maxLimit
                balance = BigDecimal.valueOf(rand.nextDouble() * maxLimit.doubleValue()).setScale(2, RoundingMode.HALF_UP);
            }

            //использование конструктора для заполнения объекта класса ResponseDTO
            ResponseDTO responseDTO = new ResponseDTO(
                    requestGTO.getRqUID(),
                    clientID,
                    requestGTO.getAccount(),
                    currency,
                    balance,
                    maxLimit
            );

            //логирование
            log.info("********** RequestGTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestGTO));
            log.info("********** ResponseDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
