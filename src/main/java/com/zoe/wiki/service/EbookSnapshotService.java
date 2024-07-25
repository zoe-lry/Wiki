package com.zoe.wiki.service;

import com.zoe.wiki.mapper.EbookSnapshotMapperCust;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EbookSnapshotService {

    @Resource
    private EbookSnapshotMapperCust ebookSnapshotMapperCust;

    public void genSnapshot() {
        ebookSnapshotMapperCust.genSnapshot();
    }

}
