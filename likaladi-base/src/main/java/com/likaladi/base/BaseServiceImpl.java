package com.likaladi.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.likaladi.error.ErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private CommonMapper<T> commonMapper;

    /** 当前泛型真实类型的Class */
    private Class<T> modelClass;

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
    public void deleteByIdList(List ids) {
        if(commonMapper.deleteByIdList(ids) == 0){
            ErrorBuilder.throwMsg("批量删除失败");
        }
    }

    @Override
    public void deleteByCondition(T t) {
        if(commonMapper.delete(t) <= 0){
            ErrorBuilder.throwMsg("删除失败");
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
    public List<T> findListBy(String fieldName, Object value) {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return commonMapper.select(model);
        } catch (ReflectiveOperationException e) {
            log.error("查询对象信息失败：", e);
            ErrorBuilder.throwMsg("查询对象信息失败");
        }
        return null;
    }

    @Override
    public List<T> findListBy(List<String> fieldNames, List<Object> values) {
        try {
            T model = modelClass.newInstance();
            for(int i = 0; i < fieldNames.size(); i++){
                Field field = modelClass.getDeclaredField(fieldNames.get(i));
                field.setAccessible(true);
                field.set(model, values.get(i));
            }
            return commonMapper.select(model);
        } catch (ReflectiveOperationException e) {
            log.error("查询对象信息失败：", e);
            ErrorBuilder.throwMsg("查询对象信息失败");
        }
        return null;
    }

    @Override
    public List<T> findByIds(List ids) {
        return commonMapper.selectByIdList(ids);
    }

    @Override
    public List<T> findAll() {
        return commonMapper.selectAll();
    }


    @Override
    public void update(T t) {
        if(commonMapper.updateByPrimaryKeySelective(t) == 0){
            ErrorBuilder.throwMsg("更新失败");
        }
    }

    public Integer getPage(Map<String, Object> params){
        try{
            if(!Objects.isNull(params.get("page"))){
                return (Integer) params.get("page");
            }
        }catch (Exception e){

        }
        return 1;
    }

    public Integer getRows(Map<String, Object> params){
        try{
            if(!Objects.isNull(params.get("rows"))){
                return (Integer) params.get("rows");
            }
        }catch (Exception e){

        }
        return 10;
    }

}
