package com.chenmeng.project.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.metadata.data.WriteCellData;


/**
 * 告警级别转换器
 *
 * @author 沉梦听雨
 * @date 2023/11/10 10:11
 **/
public class AlertLevelConvert implements Converter<String> {

	private static final String I = "I级";
	private static final String II = "II级";
	private static final String III = "III级";

	@Override
	public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) throws Exception {
		String value = context.getValue();
		return new WriteCellData<>(I);

	}
}
