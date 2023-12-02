package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.model.TblUser;
import generator.service.TblUserService;
import generator.mapper.TblUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 沉梦
* @description 针对表【tbl_user】的数据库操作Service实现
* @createDate 2023-12-01 23:59:11
*/
@Service
public class TblUserServiceImpl extends ServiceImpl<TblUserMapper, TblUser>
    implements TblUserService{

}




