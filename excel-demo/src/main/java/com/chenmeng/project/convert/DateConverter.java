package com.chenmeng.project.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date转换类
 *
 * @author 沉梦听雨
 * @date 2023/11/10 16:01
 **/
public class DateConverter implements Converter<Date> {
	private static  final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd HH:mm:ss";

	@Override
	public Class<Date> supportJavaTypeKey() {
		return Date.class;
	}

	/**
	 * easyExcel导出数据类型转换
	 * @param cellData
	 * @param contentProperty
	 * @param globalConfiguration
	 * @return
	 * @throws Exception
	 */
	@Override
	public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
		String value = cellData.getStringValue();
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD);
		return sdf.parse(value);
	}

	/**
	 * easyExcel导入Date数据类型转换
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@Override
	public WriteCellData<String> convertToExcelData(WriteConverterContext<Date> context) throws Exception {
		Date date = context.getValue();
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD);
		return new WriteCellData<>(sdf.format(date));
	}

}
