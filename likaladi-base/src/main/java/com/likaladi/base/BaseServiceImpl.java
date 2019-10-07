package com.likaladi.base;


import com.github.pagehelper.PageHelper;
import com.likaladi.error.ErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;


@Slf4j
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private CommonMapper<T> commonMapper;

    private Class<T> modelClass; // 当前泛型真实类型的Class

    public BaseServiceImpl() {
        // 获得具体model，通过反射来根据属性条件查找数据
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T t) {
        if(commonMapper.insertSelective(t) == 0){
            ErrorBuilder.throwMsg("保存失败");
        }
    }

    @Override
    public void save(List<T> models) {
        if(commonMapper.insertList(models) == 0){
            ErrorBuilder.throwMsg("批量保存失败");
        }
    }

    @Override
    public void deleteById(Object id) {
        if(commonMapper.deleteByPrimaryKey(id) == 0){
            ErrorBuilder.throwMsg("删除失败");
        }
    }

    @Override
    public void deleteByIds(String ids) {
        if(commonMapper.deleteByIds(ids) == 0){
            ErrorBuilder.throwMsg("批量删除失败");
        }
    }

    @Override
    public T findById(Object id) {
        return commonMapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return commonMapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            log.error("查询对象信息失败：", e);
            ErrorBuilder.throwMsg("查询对象信息失败");
        }
        return null;
    }

    @Override
    public List<T> findByIds(String ids) {
        return commonMapper.selectByIds(ids);
    }

    @Override
    public List<T> findAll() {
        return commonMapper.selectAll();
    }

    @Override
    public List<T> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return commonMapper.selectAll();
    }

    @Override
    public List<T> findByPage(int pageNum, int pageSize, T entity) {
        PageHelper.startPage(pageNum,pageSize);
        return commonMapper.select(entity);
    }

    @Override
    public void update(T t) {
        if(commonMapper.updateByPrimaryKey(t) == 0){
            ErrorBuilder.throwMsg("更新失败");
        }
    }

    @Override
    public void updateSelect(T t) {
        if(commonMapper.updateByPrimaryKeySelective(t) == 0){
            ErrorBuilder.throwMsg("更新失败");
        }
    }

}
