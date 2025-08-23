package com.hanium.smartdispenser.dispenser.domain;

import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dispenser_sauce")
public class DispenserSauce {

    @Id @GeneratedValue
    @Column(name = "dispenser_sauce_id")
    private Long id;
    private int slot;

    @Column(name = "is_low")
    private boolean isLow;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dispenser_id")
    private Dispenser dispenser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;


    /**
     * 연관관계 편의 메소드
     * - Dispenser.addSauce() 에서만 호출되어야 합니다.
     */
    public void assignDispenser(Dispenser dispenser) {
        this.dispenser = dispenser;
    }

    public static DispenserSauce of(int slot, Ingredient ingredient) {
        DispenserSauce ds = new DispenserSauce();
        ds.ingredient = ingredient;
        ds.slot = slot;
        return ds;
    }

    public void markLow(boolean isLow) {
        this.isLow = isLow;
    }

}
