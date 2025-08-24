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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispenser_id")
    private Long id;

    private String uuid;

    @Enumerated(EnumType.STRING)
    private DispenserStatus status;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "dispenser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DispenserSauce> dispenserSauces = new ArrayList<>();


    /**
     * Dispenser에 DispenserSauce를 등록하고 양방향 연관관계 설정합니다.
     */
    public void addSauce(DispenserSauce dispenserSauce) {
        dispenserSauces.add(dispenserSauce);
        dispenserSauce.assignDispenser(this);
    }

    public static Dispenser of(DispenserStatus status, User user, String uuid) {
        Dispenser dispenser = new Dispenser();
        dispenser.status = status;
        dispenser.user = user;
        dispenser.uuid = uuid;
        return dispenser;
    }

    public void updateSauces(int slot, boolean isLow) {
        DispenserSauce ds = dispenserSauces.stream().filter(s -> s.getSlot() == slot)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당 슬롯이 없습니다." + slot));
        ds.markLow(isLow);
    }

    public void updateStatus(DispenserStatus status) {
        this.status = status;
    }

    public void assignUser(User user) {
        this.user = user;
    }
}
