package com.interview.receipt_processor.service;

import com.interview.receipt_processor.entity.Receipt;

public interface IReceiptService {
    Long createPointsId(Receipt receipt);
    Double getPointsById(Long id);
}
