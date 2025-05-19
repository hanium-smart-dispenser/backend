package com.hanium.smartdispenser.history.repository;

import com.hanium.smartdispenser.history.domain.History;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.hanium.smartdispenser.history.domain.QHistory.history;

@Repository
public class HistoryQueryRepositoryImpl implements HistoryQueryRepository {

    private final JPAQueryFactory query;

    public HistoryQueryRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    @Override
    public Page<History> findAllByUserIdWithPaging(Long userId, Pageable pageable) {
        List<History> histories = query.select(history)
                .from(history)
                .where(history.user.id.eq(userId))
                .limit(pageable.getPageNumber())
                .offset(pageable.getOffset())
                .fetch();

        return PageableExecutionUtils.getPage(histories, pageable,
                () -> Optional.ofNullable(
                        query.select(history.count())
                                .from(history)
                                .where(history.user.id.eq(userId))
                                .fetchOne()).orElse(0L));
    }

    @Override
    public Page<History> findAllByUserIdAndDispenserIdWithPaging(Long userId, Long dispenserId, Pageable pageable) {
        List<History> histories = query.select(history)
                .from(history)
                .where(history.user.id.eq(userId).and(history.dispenser.id.eq(dispenserId)))
                .limit(pageable.getPageNumber())
                .offset(pageable.getOffset())
                .fetch();

        return PageableExecutionUtils.getPage(histories, pageable,
                () -> Optional.ofNullable(
                        query.select(history.count())
                                .from(history)
                                .where(history.user.id.eq(userId).and(history.dispenser.id.eq(dispenserId)))
                                .fetchOne()).orElse(0L));
    }
}
