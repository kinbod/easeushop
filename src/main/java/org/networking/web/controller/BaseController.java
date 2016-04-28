package org.networking.web.controller;

import org.networking.entity.BaseEntity;
import org.networking.service.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gino on 9/20/2015.
 */
@Controller
public abstract class BaseController<T extends BaseEntity> {

    @Autowired
    protected BaseService<T> baseService;

    private TransactionTemplate transactionTemplate;

    @RequestMapping(value = "/{id}", produces = {"application/json"})
    public @ResponseBody T get(@PathVariable Long id) {
        return baseService.load(id);
    }

    @RequestMapping(value = "/list", produces = {"application/json"})
    public @ResponseBody List<T> list() {
        return baseService.list();
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST}, produces = {"application/json"}, consumes = {"application/json"})
    public @ResponseBody Map<String, Object> create(@RequestBody final T t) {
        Map<String, Object> map = new HashMap<>();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                preCreate(t);
                baseService.save(t);
                postCreate(t);
            }
        });
        map.put("object", t);
        return map;
    }

    protected void preCreate(T t) {

    }

    protected void postCreate(T t) {

    }

    protected void preUpdate(T t) {

    }

    protected void postUpdate(T t) {

    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT}, produces = {"application/json"}, consumes = {"application/json"})
    public @ResponseBody Map<String, Object> update(@PathVariable final Long id, @RequestBody final T t) {
        final Map<String, Object> map = new HashMap<>();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                preUpdate(t);
                T inDb = baseService.load(id);
                BeanUtils.copyProperties(t, inDb);
                baseService.save(inDb);
                postUpdate(t);
                map.put("object", inDb);
            }
        });
        return map;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {"application/json"})
    public @ResponseBody Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<>();
        baseService.delete(id);
        return map;
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

}
