package com.candidatetestspecification.dtos.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ProductRequestDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date entryDate;

    private String itemCode;

    private String itemName;

    private Long itemQuantity;

    private String status;
}
