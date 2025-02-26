package com.tcs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClientEntity{

    @Id
    @EqualsAndHashCode.Include
    @Column("person_id")
    private Long personId;

    @Column("identificacion")
    private String identificacion;

    @Column("name")
    private String name;

    @Column("gender")
    private String gender;

    @Column("age")
    private Integer age;

    @Column("address")
    private String address;

    @Column("phone")
    private String phone;

    @Column("client_id")
    private String clientId;

    @Column("password")
    private String password;

    @Column("status")
    private Boolean status;

    // Campos de auditoria
    @Column("created_by_user")
    private String createdByUser;

    @Column("created_date")
    private LocalDateTime createdDate;

    @Column("last_modified_by_user")
    private String lastModifiedByUser;

    @Column("last_modified_date")
    private LocalDateTime lastModifiedDate;
}
