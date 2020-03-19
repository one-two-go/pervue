package com.sunhao.pervue.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author sunhao
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlanVo extends Plan {

    private Integer ddid;

    private String ddname;

}
