package com.example.demo.controller;

import com.example.demo.entity.Revenue;
import com.example.demo.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/revenues")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @PostMapping("/create")
    public ResponseEntity<Revenue> createRevenue() {
        Revenue revenue = revenueService.createRevenue();
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotalRevenue(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        double total = revenueService.calculateTotalRevenue(start, end);
        return ResponseEntity.ok(total);
    }
}

