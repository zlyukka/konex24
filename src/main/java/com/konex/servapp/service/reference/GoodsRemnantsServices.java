package com.konex.servapp.service.reference;

import com.konex.servapp.entity.reference.GoodsRemnants;

import java.util.List;

/**
 * Created by Виталий on 27.10.2016.
 */
public interface GoodsRemnantsServices {
    List<GoodsRemnants> gatRemnantsByPartName(String name, List tochOtbor);
}
