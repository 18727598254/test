package com.hx.dao;

import com.hx.base.BaseDao;
import com.hx.domain.entity.Picture;


public interface PictureDao extends BaseDao<Picture, Long> {

    /**
     * 根据 Mds 值查询文件
     * @param code 值
     * @return /
     */
    Picture findByMd5Code(String code);

    /**
     * 根据连接地址查询
     * @param url /
     * @return /
     */
    boolean existsByUrl(String url);
}
