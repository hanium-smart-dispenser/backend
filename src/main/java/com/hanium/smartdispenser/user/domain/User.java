package com.hanium.smartdispenser.user.domain;

import com.hanium.smartdispenser.common.domain.BaseEntity;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.history.domain.History;
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

    @OneToMany(mappedBy = "user")
    List<History> historyList = new ArrayList<>();


    /**
     * 사용자에게 Dispenser를 등록하고 양방향 연관관계 설정합니다.
     */
    public void addDispenser(Dispenser dispenser) {
        dispenserList.add(dispenser);
        dispenser.assignUser(this);
    }

    /**
     * 사용자에게 History를 추가하고 양방향 연관관계 설정합니다.
     */
    public void addHistory(History history) {
        historyList.add(history);
        history.assignUser(this);
    }
}
