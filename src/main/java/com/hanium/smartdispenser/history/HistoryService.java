package com.hanium.smartdispenser.history;

import com.hanium.smartdispenser.history.domain.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;

//    public History createHistory() {
//
//    }

}
