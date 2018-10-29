package com.trxmgmt.controller;



import java.time.Instant;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trxmgmt.model.Transaction;
import com.trxmgmt.service.TrxStatService;

/**
 * RESTful controller for transaction statistics.
 *
 * @author Sushil Mukundappanavar
 */
@RestController
public class StatisticsController {
	
	@Autowired
    private TrxStatService trxStatService;



    @PostMapping("/transactions")
    public ResponseEntity postTransaction(@RequestBody Transaction transaction) {
/*		int temp = new Random().nextInt(300000);
		long temp1 = Instant.now().toEpochMilli();
		transaction.setTimestamp(temp1-temp);*/
        if (trxStatService.insertTransaction(transaction)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/statistics")
    public ResponseEntity getStatistics() {
        return ResponseEntity.ok(trxStatService.getStatistics());
    }

}
