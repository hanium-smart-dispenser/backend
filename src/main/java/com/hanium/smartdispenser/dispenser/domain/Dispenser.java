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
    private String name;

    @Enumerated(EnumType.STRING)
    private DispenserStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", unique = true)
    // 매핑은 1:n 이지만 비지니스 적으로 1대1 mapping 할거라 unique 제약 설정
    private User user;

    @OneToMany(mappedBy = "dispenser")
    private List<DispenserSource> dispenserSourceList = new ArrayList<>();

    /**
     * 연관관계 편의 메소드
     * - User.addDispenser() 에서만 호출되어야 합니다.
     */
    public void assignUser(User user) {
        this.user = user;
    }

    /**
     * Dispenser에 DispenserSource를 등록하고 양방향 연관관계 설정합니다.
     */
    public void addSource(DispenserSource dispenserSource) {
        dispenserSourceList.add(dispenserSource);
        dispenserSource.assignDispenser(this);
    }

    public static Dispenser of(String name, DispenserStatus status, User user) {
        Dispenser dispenser = new Dispenser();
        dispenser.name = name;
        dispenser.status = status;
        dispenser.user = user;

        return dispenser;
    }

    public void updateStatus(DispenserStatus status) {
        this.status = status;
    }
}
