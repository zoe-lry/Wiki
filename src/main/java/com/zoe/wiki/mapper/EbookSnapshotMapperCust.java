package com.zoe.wiki.mapper;

import com.zoe.wiki.resp.StatisticResp;
import java.util.List;

public interface EbookSnapshotMapperCust {

    public void genSnapshot();
    List<StatisticResp> getStatistic();

}
