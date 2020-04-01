package com.ncu.springboot.provider.user.bizservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.oauth2.bizservice.DeptBizService;
import com.ncu.springboot.biz.entity.Dept;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.provider.oauth2.service.impl.DeptService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
@Service
public class DeptBizServiceImpl implements DeptBizService {

    @Resource
    private DeptService deptService;

    @Override
    public Dept getDataById(String deptId) {
        return deptService.getDataById(deptId);
    }

    @Override
    public void remove(String id) {
        deptService.remove(id);
    }

    @Override
    public int insert(Dept dept) {
      return   deptService.insert(dept);
    }

    @Override
    public Res update(Dept dept) {
        return deptService.update(dept);
    }


    @Override
    public List<Dept> deptList() {
        return deptService.deptList();
    }

    @Override
    public List<Dept> selectDept(List<Dept> deptList) {
        return deptService.selectDept(deptList);
    }

    @Override
    public Dept find(Dept dept) {
        return deptService.find(dept);
    }

	@Override
	public List<String> getChild(List<String> deptIds) {
		List<String> childIds = deptService.getChild(deptIds);
		if (childIds!=null && childIds.size()>0) {
			childIds.addAll(getChild(childIds));
		}
		return childIds;
	}

}
