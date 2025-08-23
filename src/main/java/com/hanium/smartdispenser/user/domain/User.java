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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String uuid;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToOne
    private Dispenser dispenser;

    @OneToMany(mappedBy = "user")
    List<History> historyList = new ArrayList<>();


    public static User of(String password, String email, String uuid) {
        User user = new User();
        user.uuid = uuid;
        user.password = password;
        user.email = email;
        user.userRole = UserRole.ROLE_GUEST;
        return user;
    }

    /**
     * 사용자에게 History를 추가하고 양방향 연관관계 설정합니다.
     */
    public void addHistory(History history) {
        historyList.add(history);
        history.assignUser(this);
    }

    public void convertGuestToUser() {
        this.userRole = UserRole.ROLE_USER;
    }
}
