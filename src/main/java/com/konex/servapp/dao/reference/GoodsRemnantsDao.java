package com.konex.servapp.dao.reference;

import com.konex.servapp.entity.reference.GoodsRemnants;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Виталий on 27.10.2016.
 */

public interface GoodsRemnantsDao{
    @Query("select ost from GoodsRemnants ost " +
            "LEFT JOIN ost.goods g " +
            "LEFT JOIN ost.tradePoint tp" +
            "where g.name like :#{#goodsName}+'%'")
    List<GoodsRemnants> getOstByToch(@Param("goodsName") String tovName);
}
