package com.interview.receipt_processor.service.impl;

import com.interview.receipt_processor.entity.Item;
import com.interview.receipt_processor.entity.Receipt;
import com.interview.receipt_processor.service.IReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReceiptServiceImpl implements IReceiptService {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);
    private final Map<String, Double> receiptStore = new HashMap<>(); //  Stores data in memory

    // Constants for Calculation Numbers
    private static final int BONUS_FULL_DOLLAR_AMOUNT = 50;
    private static final int BONUS_DIVISIBLE_BY_25 = 25;
    private static final int BONUS_EVEN_DAY = 6;
    private static final int BONUS_PURCHASE_TIME = 10;
    private static final double ITEM_BONUS_PERCENTAGE = 0.2;

    @Override
    public String createPointsId(Receipt receipt) {
        if (!isValidReceipt(receipt)) {
            throw new IllegalArgumentException("Invalid receipt data. Please verify input.");
        }

        String receiptId = UUID.randomUUID().toString();
        double points = calculatePoints(receipt);

        logger.info("Generated Receipt ID: {} with {} points", receiptId, points);
        receiptStore.put(receiptId, points);

        return receiptId; // ✅ Return String ID instead of Long
    }

    public Double getPointsById(String id) {
        return receiptStore.getOrDefault(id,null);
    }

    private boolean isValidReceipt(Receipt receipt) {
        return receipt.getRetailer() != null && !receipt.getRetailer().isEmpty()
                && receipt.getPurchaseDate() != null
                && receipt.getPurchaseTime() != null
                && receipt.getItems() != null && !receipt.getItems().isEmpty()
                && receipt.getTotal() != null && receipt.getTotal().matches("\\d+\\.\\d{2}");
    }

    private double calculatePoints(Receipt receipt) {
        double points = 0;
        logger.info("Calculating points for receipt from {}", receipt.getRetailer());

        points += calculateRetailerPoints(receipt.getRetailer());
        points += calculateTotalPoints(receipt.getTotal());
        points += calculateItemPoints(receipt.getItems());
        points += calculateDatePoints(receipt.getPurchaseDate());
        points += calculateTimePoints(receipt.getPurchaseTime());

        return points;
    }

    private double calculateRetailerPoints(String retailer) {
        double points = 0;
        for (int i = 0; i < retailer.length(); i++) {
            if (Character.isLetterOrDigit(retailer.charAt(i))) {
                points += 1;
            }
        }
        return points;
    }


    private double calculateTotalPoints(String total) {
        double totalValue = Double.parseDouble(total);
        double points = 0;
        if (totalValue % .25 == 0) {
            points += BONUS_DIVISIBLE_BY_25;
        }

        for (int i = 0; i < total.length(); i++) {
            if (total.charAt(i) == '.') {
                if (total.charAt(i + 1) - '0' == 0 || total.charAt(i + 2) - '0' == 0) {
                    points += BONUS_FULL_DOLLAR_AMOUNT;
                }
            }
        }
        return points;
    }

    private double calculateItemPoints(List<Item> items) {
        double points = (items.size() / 2) * 5;

        points += items.stream()
                .filter(item -> item.getShortDescription().trim().length() % 3 == 0)
                .mapToDouble(item -> Math.ceil(Double.parseDouble(item.getPrice()) * ITEM_BONUS_PERCENTAGE))
                .sum();

        return points;
    }

    private double calculateDatePoints(String purchaseDate) {
        double points = 0;
        String dayStr = purchaseDate.substring(purchaseDate.length() - 2);
        int dayInt = Integer.parseInt(dayStr);
        if(dayInt % 2 == 1) {
            points += BONUS_EVEN_DAY;
        }
        return points;
    }

    private double calculateTimePoints(String purchaseTime) {
        int time = Integer.parseInt(purchaseTime.replace(":", ""));
        return (time > 1400 && time < 1600) ? BONUS_PURCHASE_TIME : 0;
    }

}
