package com.surya.currencyConvertor.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {
    private static final Map<String ,Double> exchangeRate=new HashMap<>();
    static {
        exchangeRate.put("USD",1.0);
        exchangeRate.put("INR", 88.68);
        exchangeRate.put("EUR", 0.85);
        exchangeRate.put("GBP", 0.74);
        exchangeRate.put("JPY", 147.12);
        exchangeRate.put("SGD", 1.36);
    }
    @PostMapping("/convert")
    public ResponseEntity<Map<String, Object>> convertCurrency(@RequestBody Map<String , String> request)
        {
        String from=request.get("from");
        String to =request.get("to");
        Double amount;
        Map<String, Object> response=new HashMap<>();
        try {
            amount=Double.parseDouble(request.get("amount"));
        }
        catch (Exception e){
            response.put("error", "Invalid amount format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (!exchangeRate.containsKey(from) || !exchangeRate.containsKey(to)) {
            response.put("error", "Invalid currency code");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);


        }
        double amountInUsd= amount/exchangeRate.get(from);
        double convertedAmount=amountInUsd*exchangeRate.get(to);
        response.put("from", from);
        response.put("to", to);
        response.put("originalAmount", amount);
        response.put("convertedAmount", convertedAmount);
            return ResponseEntity.ok(response);

        }

}
