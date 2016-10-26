package com.konex.servapp.dao.reference;

import com.konex.servapp.entity.reference.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Виталий on 26.10.2016.
 */
public interface GoodDao extends JpaRepository<Good, Long> {
    @Query("select g from Good g where g.name like :#{#goodName}+'%'")
    List<Good> getGoodByPartName(@Param("goodName") String name);
}
