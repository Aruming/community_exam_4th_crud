package com.ll.exam.article.repository;

import com.ll.exam.annotation.Autowired;
import com.ll.exam.annotation.Repository;
import com.ll.exam.article.dto.ArticleDto;
import com.ll.exam.article.service.ArticleService;
import com.ll.exam.mymap.MyMap;
import com.ll.exam.mymap.SecSql;

import java.util.List;

@Repository
public class ArticleRepository {
    @Autowired
    private MyMap myMap;

    public List<ArticleDto> getArticles() {
        SecSql sql = myMap.genSecSql();
        sql
                .append("SELECT *")
                .append("FROM article")
                .append("ORDER BY id DESC");
        return sql.selectRows(ArticleDto.class);
    }

    public ArticleDto getArticleById(int id) {
        SecSql sql = myMap.genSecSql();
        sql.append("SELECT * FROM article WHERE id = ?", id);
        ArticleDto articleDto = sql.selectRow(ArticleDto.class);

        return articleDto;
    }

    public long getArticlesCount() {
        SecSql sql = myMap.genSecSql();
        sql.append("SELECT COUNT(*) FROM article");

        Long count = sql.selectLong();

        return count;
    }
}
