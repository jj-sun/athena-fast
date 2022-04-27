package com.athena.common.utils;

import com.athena.common.base.dto.PageDto;
import com.athena.common.xss.SQLFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

/**
 * 查询参数
 *
 * @author Mr.sun
 */
public class Query {

    public static Pageable getPage(PageDto pageDto) {
        //分页参数
        int curPage = 0;
        int limit = 10;

        //jpa默认是从0开始
        if(Objects.nonNull(pageDto.getPage())) {
            curPage = pageDto.getPage().intValue() - 1;
        }
        if(Objects.nonNull(pageDto.getPageSize())) {
            limit = pageDto.getPageSize().intValue();
        }

        //分页对象
        Pageable page = PageRequest.of(curPage, limit);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject(pageDto.getSidx());
        String order = pageDto.getOrder();


        //前端字段排序
        /*if(StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)){
            if(Constant.ASC.equalsIgnoreCase(order)) {
                return  page.addOrder(OrderItem.asc(orderField));
            }else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if(StringUtils.isBlank(pageDto.getDefaultOrderField())){
            return page;
        }

        //默认排序
        if(pageDto.isAsc()) {
            page.addOrder(OrderItem.asc(pageDto.getDefaultOrderField()));
        }else {
            page.addOrder(OrderItem.desc(pageDto.getDefaultOrderField()));
        }
*/
        return page;
    }
}
