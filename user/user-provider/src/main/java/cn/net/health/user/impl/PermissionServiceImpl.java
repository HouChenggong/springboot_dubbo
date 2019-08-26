package cn.net.health.user.impl;

import cn.net.health.user.code.ResourceType;
import cn.net.health.user.dto.MenuInfo;
import cn.net.health.user.dto.PermissionInfo;
import cn.net.health.user.entity.SysPermission;
import cn.net.health.user.ex.BusinessException;
import cn.net.health.user.mapper.SysPermissionMapper;
import cn.net.health.user.service.IPermissionService;
import cn.net.health.user.util.Constant;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统权限表 服务实现类
 * </p>
 *
 * @author Auto Generator
 * @since 2018-07-16
 */
@Slf4j
@Service(version = "1.0.0", timeout = 60000)
public class PermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements IPermissionService {

    @Cacheable("permissionCache")
    @Override
    public List<SysPermission> getAllPermissions() {
        QueryWrapper<SysPermission> qw1 = new QueryWrapper<SysPermission>();
        List<SysPermission> permissions = this.baseMapper.selectList(qw1);
        return permissions;
    }

    @CacheEvict(value = "permissionCache", allEntries = true)
    @Override
    public Boolean savePermission(SysPermission permission) {
        Boolean res = false;
        if (permission.getPermissionId() == null) {
            if (permission.getParentId() == null) {
                permission.setParentId("0");
                permission.setParentIds("0");
            } else {
                SysPermission ps = this.baseMapper.selectById(permission.getParentId());
                permission.setParentIds(ps.getParentIds() + "/" + ps.getPermissionId());
            }
            res = this.save(permission);
        } else {
            res = this.updateById(permission);
        }
        return res;
    }

    @CacheEvict(value = "permissionCache", allEntries = true)
    @Override
    public Boolean delBatchPermission(List<Integer> ids) {
        Boolean res = false;
        //目录和菜单只能单个删除
        if (ids.size() == 1) {
            SysPermission permission = this.getById(ids.get(0));
            SysPermission con = new SysPermission();
            con.setParentId(permission.getPermissionId());
            List<SysPermission> list = this.baseMapper.selectList(new QueryWrapper<>(con));
            if (list != null && list.size() > 0) {
                throw new BusinessException(Constant.YES_ERROR, "有子权限不能删除！");
            }
            res = this.removeById(ids.get(0));
        } else {
            res = this.baseMapper.deleteBatchIds(ids) > 0;
        }
        return res;
    }

    @Cacheable(value = "permissionRole", key = "'roleId_'+#roleId")
    @Override
    public Set<String> allPermissionByRoleId(String roleId) {
        log.info("没有缓存的情况下，进行查询...................."+roleId);
        return this.baseMapper.allPermissionByRoleId(roleId);
    }

    @Cacheable(value = "permissionCache", key = "'code:'+#p0")
    @Override
    public List<MenuInfo> getMenuPermissions(String code) {
        List<MenuInfo> menuInfoList = new ArrayList<>();
        QueryWrapper<SysPermission> wrapper = new QueryWrapper<>();
        SysPermission permission = new SysPermission();
        permission.setPermissionCode(code);
        wrapper.setEntity(permission);
        permission = this.getOne(wrapper);
        SysPermission con = new SysPermission();
        con.setParentIds(permission.getParentIds() + "/" + permission.getPermissionId());
        wrapper.setEntity(con);
        String[] resourceTypes = {ResourceType.DIRECTORY.getCode(), ResourceType.MENU.getCode()};
        wrapper.in("resource_type", resourceTypes);
        List<SysPermission> list = this.list(wrapper);
        for (SysPermission ps : list) {
            MenuInfo info = new MenuInfo();
            info.setOnlyId(ps.getPermissionId());
            info.setTitle(ps.getPermissionName());
            info.setHref(ps.getUrl());
            if (ps.getResourceType().equals(ResourceType.DIRECTORY.getCode())) {
                con = new SysPermission();
                con.setParentIds(ps.getParentIds() + "/" + ps.getPermissionId());
                wrapper.setEntity(con);
                List<SysPermission> list2 = this.list(wrapper);
                List<MenuInfo> subMenuInfoList = new ArrayList<>();
                for (SysPermission subPs : list2) {
                    MenuInfo subInfo = new MenuInfo();
                    subInfo.setOnlyId(subPs.getPermissionId());
                    subInfo.setTitle(subPs.getPermissionName());
                    subInfo.setHref(subPs.getUrl());
                    subMenuInfoList.add(subInfo);
                }
                info.setChildren(subMenuInfoList);
            }
            menuInfoList.add(info);
        }
        return menuInfoList;
    }

    @Cacheable(value = "permissionCache")
    @Override
    public List<SysPermission> getTopDirectoryPermissions() {
        SysPermission permission = new SysPermission();
        permission.setResourceType(ResourceType.TOP_DIRECTORY.getCode());
        return this.list(new QueryWrapper<>(permission));
    }

}
