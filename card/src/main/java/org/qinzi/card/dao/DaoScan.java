package org.qinzi.card.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan(basePackages = "org.qinzi.card.dao")
public class DaoScan {
}
