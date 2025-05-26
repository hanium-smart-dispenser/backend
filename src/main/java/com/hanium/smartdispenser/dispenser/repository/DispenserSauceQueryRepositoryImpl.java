package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.DispenserSauce;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hanium.smartdispenser.dispenser.domain.QDispenserSauce.*;


@Repository
public class DispenserSauceQueryRepositoryImpl implements DispenserSauceQueryRepository {

    private final JPAQueryFactory query;

    public DispenserSauceQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<DispenserSauce> findAllByDispenser(Long dispenserId) {
        return query.selectFrom(dispenserSauce)
                .where(dispenserSauce.dispenser.id.eq(dispenserId))
                .fetch();
    }
}
