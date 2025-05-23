package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static com.hanium.smartdispenser.dispenser.domain.QDispenser.*;

@Repository
public class DispenserRepositoryImpl implements DispenserQueryRepository {

    private final JPAQueryFactory query;

    public DispenserRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    @Override
    public Dispenser findByUser(Long userId) {
        return query.selectFrom(dispenser)
                .where(dispenser.user.id.eq(userId))
                .fetchOne();
    }
}
