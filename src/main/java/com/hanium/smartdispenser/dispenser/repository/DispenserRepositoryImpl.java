package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.hanium.smartdispenser.dispenser.domain.QDispenser.*;

@Repository
public class DispenserRepositoryImpl implements DispenserQueryRepository {

    private final JPAQueryFactory query;

    public DispenserRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Dispenser> findAllByUserIdWithPaging(Long userId, Pageable pageable) {
        List<Dispenser> dispensers = query.select(dispenser)
                .from(dispenser)
                .where(dispenser.user.id.eq(userId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        return PageableExecutionUtils.getPage(dispensers, pageable,
                () -> Optional.ofNullable(query.select(dispenser.count())
                        .from(dispenser)
                        .where(dispenser.user.id.eq(userId))
                        .fetchOne()).orElse(0L));
    }
}
