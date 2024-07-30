package com.chenmeng.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import com.chenmeng.common.model.dto.ReadErrorModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 基础通用导入Excel读取监听器
 *
 * @author chenmeng
 */
@Slf4j
public class BaseExcelReadListener<T> implements ReadListener<T> {

  /**
   * 总记录数。
   */
  @Getter
  private int count;

  /**
   * 批次大小。
   */
  private final int batchSize;

  /**
   * 缓存数据。
   */
  private final List<T> cacheData;

  /**
   * 消费函数。
   */
  private final Consumer<List<T>> consumer;

  @Getter
  private ReadErrorModel readErrorModel;

  /**
   * 构造函数。
   *
   * @param consumer  消费函数。
   */
  public BaseExcelReadListener(Consumer<List<T>> consumer) {
    this(0, 1000, consumer);
  }

  /**
   * 构造函数。
   *
   * @param count 总记录数。
   * @param batchSize 批次大小。
   * @param consumer  消费函数。
   */
  public BaseExcelReadListener(int count, int batchSize, Consumer<List<T>> consumer) {
    this.count = count;
    this.batchSize = batchSize;
    this.consumer = consumer;
    this.cacheData = new ArrayList<>(this.batchSize);
  }

  /**
   * 读取数据。
   *
   * @param data    数据。
   * @param context 上下文。
   */
  @Override
  public void invoke(T data, AnalysisContext context) {
    this.cacheData.add(data);
    if (this.cacheData.size() >= this.batchSize) {
      this.consumer.accept(this.cacheData);
      this.count += this.cacheData.size();
      this.cacheData.clear();
    }
  }

  /**
   * 异常处理。
   *
   * @param exception 异常。
   * @param context   上下文。
   */
  @Override
  public void onException(Exception exception, AnalysisContext context) throws Exception {
    if (exception instanceof ExcelDataConvertException) {
      ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
      // 行索引。
      int rowIndex = excelDataConvertException.getRowIndex();
      // 列索引。
      int columnIndex = excelDataConvertException.getColumnIndex();
      // 单元格数据。
      String cellData = excelDataConvertException.getCellData().getStringValue();
      // 异常原因。
      String reason = exception.getMessage();
      this.readErrorModel = new ReadErrorModel(rowIndex, columnIndex, cellData, reason);
      throw new ExcelAnalysisStopException();
    }
    throw exception;
  }

  /**
   * 解析完成后进行的操作。
   *
   * @param context 上下文。
   */
  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    if (!this.cacheData.isEmpty()) {
      this.consumer.accept(this.cacheData);
      this.count += this.cacheData.size();
    }
  }
}
