package com.hanium.smartdispenser.dispenser.domain;

import com.hanium.smartdispenser.common.domain.BaseEntity;
import com.hanium.smartdispenser.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private DispenserStatus status;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "dispenser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DispenserSauce> dispenserSauceList = new ArrayList<>();


    /**
     * Dispenser에 DispenserSauce를 등록하고 양방향 연관관계 설정합니다.
     */
    public void addSauce(DispenserSauce dispenserSauce) {
        dispenserSauceList.add(dispenserSauce);
        dispenserSauce.assignDispenser(this);
    }

    public static Dispenser of(DispenserStatus status, User user) {
        Dispenser dispenser = new Dispenser();
        dispenser.status = status;
        dispenser.user = user;

        return dispenser;
    }

    public void updateStatus(DispenserStatus status) {
        this.status = status;
    }
}
