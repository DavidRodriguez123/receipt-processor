package com.interview.receipt_processor.entity;
import lombok.*;

import java.util.List;


@Getter @Setter
public class Receipt {
   private Long receiptId;
   private String retailer;
   private String purchaseDate;
   private String purchaseTime;
   private List<Item> items;
   private String total;

}
