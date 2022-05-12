package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.entity.History;
import com.example.mapper.HistoryMapper;
import com.example.service.HistoryService;
import org.springframework.stereotype.Service;

/**
 * 系统 - 日志表(History)表服务实现类
 *
 */
@Service("HistoryService")
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

}