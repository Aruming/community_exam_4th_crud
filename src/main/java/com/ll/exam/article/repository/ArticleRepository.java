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

    public ArticleDto getArticleById(long id) {
        SecSql sql = myMap.genSecSql();
        sql.append("SELECT * FROM article WHERE id = ?", id);

        ArticleDto articleDto = sql.selectRow(ArticleDto.class);

        return articleDto;
    }

    public long getArticlesCount() {
        SecSql sql = myMap.genSecSql();
        sql.append("SELECT COUNT(*) FROM article");

        long count = sql.selectLong();

        return count;
    }

    public long write(String title, String body, boolean isBlind) {
        SecSql sql = myMap.genSecSql();
        sql
                .append("INSERT INTO article")
                .append("SET createdDate = NOW()")
                .append(", modifiedDate = NOW()")
                .append(", title = ?", title)
                .append(", body = ?", body)
                .append(", isBlind = ?", isBlind);

        long newArticleId = sql.insert();

        return newArticleId;
    }

    public void modify(long id, String title, String body, boolean isBlind) {
        SecSql sql = myMap.genSecSql();

        sql
                .append("UPDATE article")
                .append("SET modifiedDate = NOW()")
                .append(", title = ?", title)
                .append(", body = ?", body)
                .append(", isBlind = ?", isBlind)
                .append("WHERE id = ?", id);

        sql.update();
    }

    public void delete(long id) {
        SecSql sql = myMap.genSecSql();
        sql
                .append("DELETE FROM article")
                .append("WHERE id = ?", id);

        sql.delete();
    }

    public ArticleDto getPrevArticle(long id) {
        SecSql sql = myMap.genSecSql();
        sql
                .append("SELECT *")
                .append("FROM article")
                .append("WHERE id < ?", id)
                .append("AND isBlind = 0")
                .append("ORDER BY id DESC")
                .append("LIMIT 1");
        return sql.selectRow(ArticleDto.class);
    }

    public ArticleDto getNextArticle(long id) {
        SecSql sql = myMap.genSecSql();
        sql
                .append("SELECT *")
                .append("FROM article")
                .append("WHERE id > ?", id)
                .append("AND isBlind = 0")
                .append("ORDER BY id ASC")
                .append("LIMIT 1");
        return sql.selectRow(ArticleDto.class);
    }
}
