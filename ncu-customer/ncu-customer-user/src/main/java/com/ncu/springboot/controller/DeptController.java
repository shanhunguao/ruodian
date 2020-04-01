package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.oauth2.bizservice.DeptBizService;
import com.ncu.springboot.biz.entity.Dept;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.util.Tree;
import com.ncu.springboot.util.TreeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Reference
    private DeptBizService deptBizService;


    @RequestMapping("/list")
    public Res list() {
        List<Dept> list = deptBizService.deptList();
        List<Tree<Dept>> treeList = deptTree(list);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", treeList);
        return Res.SUCCESS(jsonMap);
    }

    @RequestMapping("/from")
    public Res from(String deptId) {
        Dept dept = deptBizService.getDataById(deptId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fromObject", dept);
        return Res.SUCCESS(map);
    }

    /**
     * 对于部门的条件查询
     */
    @RequestMapping("find")
    public Res find(Dept dept) {
        Dept dept1 = deptBizService.find(dept);
        return Res.SUCCESS(dept1);
    }

    @RequestMapping("/save")
    public Res save(Dept dept) {
        try {
            dept.setUpdateTime(new Date());
            if (deptBizService.insert(dept)>0) {
                return Res.SUCCESS("添加部门成功");
            }
            return Res.ERROR("添加部门失败");
        } catch (Exception e) {
            return Res.ERROR("添加部门失败");
        }
    }

    @RequestMapping("/remove")
    public Res remove(String id) {
        try {
            deptBizService.remove(id);
            return Res.SUCCESS();
        } catch (Exception e) {
            return Res.ERROR();
        }
    }

    @RequestMapping("/update")
    public Res update(Dept dept) {
        try {
         return deptBizService.update(dept);
        } catch (Exception e){
            return Res.ERROR("修改部门失败");
        }

    }

    /**
     * @Description: 部门树
     * @author: czy
     * @date: 2019年10月23日 上午9:37:20
     */
    public List<Tree<Dept>> deptTree(List<Dept> list) {
        List<Tree<Dept>> trees = new ArrayList<Tree<Dept>>();
        for (Dept dept : list) {
            Tree<Dept> tree = new Tree<Dept>();
            tree.setId(String.valueOf(dept.getId()));
            tree.setText(dept.getName());
            tree.setParentId(dept.getParentId());
            Map<String, Object> state = new HashMap<String, Object>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        List<Tree<Dept>> t = TreeUtils.buildList(trees, "1");
        return t;
    }


}
