package com.chenmeng.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Excel读取错误，模型。
 *
 * @author wyy
 */
@Data
@AllArgsConstructor
public class ReadErrorModel implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 行索引。
   */
  private Integer rowIndex;

  /**
   * 列索引。
   */
  private Integer columnIndex;

  /**
   * 单元格数据。
   */
  private String cellData;

  /**
   * 错误信息。
   */
  private String message;

}
