package io.renren.modules.gen.service;

import io.renren.common.base.CrudService;
import io.renren.common.utils.Query;
import io.renren.modules.gen.dao.SysGeneratorDao;
import io.renren.modules.gen.entity.SysGeneratorEntity;
import io.renren.modules.gen.utils.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午3:33:38
 */
@Service
public class SysGeneratorService extends CrudService<SysGeneratorDao, SysGeneratorEntity> {

    public Map<String, String> queryTable(String tableName) {
        Map params = new HashMap();
        params.put("tableName", tableName);
        Query query = new Query(params);
        return dao.queryTable(query);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        Map params = new HashMap();
        params.put("tableName", tableName);
        Query query = new Query(params);
        return dao.queryColumns(query);
    }

    /**
     * 生成代码
     */
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
