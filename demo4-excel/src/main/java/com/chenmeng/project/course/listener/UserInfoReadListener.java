package com.chenmeng.project.course.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import com.chenmeng.common.model.dto.ReadErrorModel;
import com.chenmeng.project.course.constant.ExcelConstant;
import com.chenmeng.project.course.model.UserInfoModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 用户信息，读取监听器。
 */
@Slf4j
public class UserInfoReadListener implements ReadListener<UserInfoModel> {

    /**
     * 批次大小。
     */
    private final int batchSize;
    /**
     * 缓存数据。
     */
    private final List<UserInfoModel> cacheData;
    /**
     * 条件函数。
     */
    private final Predicate<UserInfoModel> predicate;
    /**
     * 消费函数。
     */
    private final Consumer<List<UserInfoModel>> consumer;
    @Getter
    private ReadErrorModel readErrorModel;

    /**
     * 构造函数。
     *
     * @param predicate 条件函数。
     * @param consumer  消费函数。
     */
    public UserInfoReadListener(Predicate<UserInfoModel> predicate, Consumer<List<UserInfoModel>> consumer) {
        this(ExcelConstant.BATCH_SIZE, predicate, consumer);
    }

    /**
     * 构造函数。
     *
     * @param batchSize 批次大小。
     * @param predicate 条件函数。
     * @param consumer  消费函数。
     */
    public UserInfoReadListener(int batchSize, Predicate<UserInfoModel> predicate, Consumer<List<UserInfoModel>> consumer) {
        this.batchSize = batchSize;
        this.predicate = predicate;
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
    public void invoke(UserInfoModel data, AnalysisContext context) {
        if (!this.predicate.test(data)) {
            return;
        }
        this.cacheData.add(data);
        if (this.cacheData.size() >= this.batchSize) {
            this.consumer.accept(this.cacheData);
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
            int rowIndex = excelDataConvertException.getRowIndex(); // 行索引。
            int columnIndex = excelDataConvertException.getColumnIndex(); // 列索引。
            String cellData = excelDataConvertException.getCellData().getStringValue(); // 单元格数据。
            String reason = exception.getMessage(); // 异常原因。
            this.readErrorModel = new ReadErrorModel(rowIndex, columnIndex, cellData, reason);
            throw new ExcelAnalysisStopException();
        }
        throw exception;
    }

    /**
     * 解析完成。
     *
     * @param context 上下文。
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (!this.cacheData.isEmpty()) {
            this.consumer.accept(this.cacheData);
        }
    }

}
