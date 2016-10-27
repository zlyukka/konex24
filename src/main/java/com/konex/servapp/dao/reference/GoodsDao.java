package com.konex.servapp.dao.reference;

import com.konex.servapp.entity.reference.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Виталий on 26.10.2016.
 */
public interface GoodsDao extends JpaRepository<Goods, Long> {
    @Query("select g from Goods g where g.name like :#{#goodsName}+'%'")
    List<Goods> getGoodsByPartName(@Param("goodsName") String name);
}
