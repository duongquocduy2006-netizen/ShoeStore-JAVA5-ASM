package com.ShoeStore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // PHẢI CÓ DÒNG NÀY
@Table(name = "colors") // Tên bảng trong SQL
@Data
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "color_name", columnDefinition = "NVARCHAR(255)")
    private String colorName;
}