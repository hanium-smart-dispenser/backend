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
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @ManyToOne(fetch = LAZY)
    private Dispenser dispenser;

    @ManyToOne(fetch = LAZY)
    private Recipe recipe;

    @CreatedDate
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private HistoryStatus status;

    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
    private String errorMessage;
}
