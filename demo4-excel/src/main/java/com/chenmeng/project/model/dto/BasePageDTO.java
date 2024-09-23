package com.chenmeng.project.model.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BasePageDTO implements Serializable {
  private static final long serialVersionUID = 8916337394266613506L;

  /** 每页显示条数，默认10 */
  protected long size = 10;

  /** 当前页 */
  protected long current = 1;

  /** 排序字段，默认升序 */
  protected List<OrderItem> ordres = new ArrayList<>();
}