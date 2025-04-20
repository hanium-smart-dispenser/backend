package com.hanium.smartdispenser.history.domain;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "history")
public class History {

    @Id @GeneratedValue
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dispenser_id")
    private Dispenser dispenser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @CreatedDate
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private HistoryStatus status;

    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private String errorMessage;

    /**
     * 연관관계 편의 메소드
     * - User.addHistory() 에서만 호출되어야 합니다.
     */
    public void assignUser(User user) {
        this.user = user;
    }
}
