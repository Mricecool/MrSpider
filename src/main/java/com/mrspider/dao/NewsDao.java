package com.mrspider.dao;

import com.mrspider.model.NewsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mr on 2017/9/28.
 */
@Repository
public interface NewsDao extends JpaRepository<NewsInfo, Long> {
}
