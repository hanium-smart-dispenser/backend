package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.DispenserSource;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hanium.smartdispenser.dispenser.domain.QDispenserSource.*;


@Repository
public class DispenserSourceQueryRepositoryImpl implements DispenserSourceQueryRepository {

    private final JPAQueryFactory query;

    public DispenserSourceQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<DispenserSource> findAllByDispenser(Long dispenserId) {
        return query.selectFrom(dispenserSource)
                .where(dispenserSource.dispenser.id.eq(dispenserId))
                .fetch();
    }
}
