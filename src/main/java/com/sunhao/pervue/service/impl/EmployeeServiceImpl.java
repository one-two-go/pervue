package com.sunhao.pervue.service.impl;

import com.sunhao.pervue.entity.Employee;
import com.sunhao.pervue.mapper.EmployeeMapper;
import com.sunhao.pervue.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ql
 * @since 2020-03-03
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
