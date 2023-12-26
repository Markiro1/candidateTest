package com.candidatetestspecification.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "entry_date")
    private Date entryDate;

    @Column(nullable = false, name = "item_code")
    private String itemCode;

    @Column(nullable = false, name = "item_name")
    private String itemName;

    @Column(nullable = false, name = "item_quantity")
    private Long itemQuantity;

    @Column(nullable = false, name = "status")
    private String status;
}
