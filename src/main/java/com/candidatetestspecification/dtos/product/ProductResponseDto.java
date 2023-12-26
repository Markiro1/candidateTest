package com.candidatetestspecification.dtos.product;

import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ProductResponseDto {
    private Date entryDate;
    private String itemCode;
    private String itemName;
    private Long itemQuantity;
    private String status;
}
