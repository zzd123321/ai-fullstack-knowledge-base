package com.example.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.order.message.MqConsumeRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MqConsumeRecordMapper extends BaseMapper<MqConsumeRecord> {
}