package com.chenmeng.project.controller;

import com.chenmeng.common.result.Result;
import com.chenmeng.project.service.UserService;
import com.chenmeng.project.vo.UserImportExcelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author chenmeng
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/import-excel")
    public Result<Boolean> importExcel() {
        ArrayList<UserImportExcelVO> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            UserImportExcelVO userImportExcelVO = new UserImportExcelVO();
            userImportExcelVO.setUserAccount("chenmeng" + i);
            userImportExcelVO.setUserPassword("123456");
            // 模拟异常的情况，看看会不会全批量数据回滚
            userImportExcelVO.setUserRole(i == 500 ? "user1111" : "admin");
            list.add(userImportExcelVO);
        }
        return Result.status(userService.importExcel(list));
    }
}
