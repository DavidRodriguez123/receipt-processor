package com.interview.receipt_processor.service;

import com.interview.receipt_processor.entity.Receipt;

public interface IReceiptService {
    String createPointsId(Receipt receipt);
    Double getPointsById(String id);
}
