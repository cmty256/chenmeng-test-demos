package com.chenmeng.project.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * 自定义自适应列宽处理策略
 *
 * @author cmty256
 * @date 2023/11/15 16:54
 **/
public class CustomColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {

  public CustomColumnWidthStyleStrategy() {
  }

  @Override
  protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    Sheet sheet = writeSheetHolder.getSheet();
    sheet.setColumnWidth(3, 8000);

  }

}
