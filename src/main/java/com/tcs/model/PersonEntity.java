package com.tcs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="person")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long personId;

    @Column(nullable = false, unique = true)
    private String identificacion;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String gender;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 100)
    private String address;

    @Column(length = 15)
    private String phone;

    // Campos de auditoria
    @Column(length = 30)
    private String createdByUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(length = 30)
    private String lastModifiedByUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
}
