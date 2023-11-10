package com.chenmeng.project.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenmeng.project.model.entity.TblExcel;
import com.chenmeng.project.model.vo.ExcelExportVO;
import com.chenmeng.project.service.TblExcelService;
import com.chenmeng.project.mapper.TblExcelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wyy
* @description 针对表【tbl_excel(Excel测试表)】的数据库操作Service实现
* @createDate 2023-11-09 15:20:40
*/
@Service
public class TblExcelServiceImpl extends ServiceImpl<TblExcelMapper, TblExcel>
    implements TblExcelService {

    @Override
    public void export(HttpServletResponse response) {

        // TODO 图片压缩
        List<TblExcel> list = this.list();
        List<ExcelExportVO> list2 = list.stream().map(item -> {
            ExcelExportVO exportVO = new ExcelExportVO();
            exportVO.setName(item.getName());
            // try {
            //     exportVO.setFile(new URL(item.getFile()));
            // } catch (MalformedURLException e) {
            //     throw new RuntimeException(e);
            // }
            return exportVO;
        }).collect(Collectors.toList());

        String title = "图片告警";

        // 4、EasyExcel导出
        try {
            // 设置内容类型
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 设置字符编码
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easy excel没有关系
            String fileName = URLEncoder.encode(title, "UTF-8").replaceAll("\\+", "%20");
            // 设置响应头
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), ExcelExportVO.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("模板")
                    .doWrite(list2);
        } catch (Exception e) {
            // 重置response
            response.reset();
            String errorMessage = "导出文件失败：" + e.getMessage();
            System.out.println("errorMessage = " + errorMessage);
        }

    }
}




