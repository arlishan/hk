package com.example.leadbot.lead.mapper;

import com.example.leadbot.config.MapStructConfig;
import com.example.leadbot.lead.Lead;
import com.example.leadbot.lead.vo.LeadVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface LeadMapper {

    @Mapping(
            target = "tags",
            expression = "java(lead.getTags() == null || lead.getTags().isBlank() ? java.util.Collections.emptyList() : java.util.Arrays.stream(lead.getTags().split(\",\")).map(String::trim).filter(s -> !s.isBlank()).toList())"
    )
    LeadVO toVO(Lead lead);

    default List<LeadVO> toVOList(List<Lead> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list.stream().map(this::toVO).toList();
    }
}
