package com.konex.servapp.dao.reference;

import com.konex.servapp.entity.reference.GoodsRemnants;
import com.konex.servapp.entity.reference.GoodsRemnantsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Created by Виталий on 27.10.2016.
 */

public interface GoodsRemnantsDao extends JpaRepository<GoodsRemnants, GoodsRemnantsId> {

    @Query("select ost from GoodsRemnants ost " +
            "LEFT JOIN ost.goods g LEFT JOIN ost.tradePoint pr where g.name like :#{#goodsName}+'%'")
    List<GoodsRemnants> getOstByToch(@Param("goodsName") String tovName);
}
