package com.hanium.smartdispenser.user.domain;

import com.hanium.smartdispenser.common.domain.BaseEntity;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String password;
    private String email;

    @OneToMany(mappedBy = "user")
    List<Dispenser> dispenserList = new ArrayList<>();


}
