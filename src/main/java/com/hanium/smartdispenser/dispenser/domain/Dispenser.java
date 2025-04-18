package com.hanium.smartdispenser.dispenser.domain;

import com.hanium.smartdispenser.common.domain.BaseEntity;
import com.hanium.smartdispenser.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dispenser")
public class Dispenser extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "dispenser_id")
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private DispenserStatus status;

    @ManyToOne(fetch = LAZY)
    private User user;

}
