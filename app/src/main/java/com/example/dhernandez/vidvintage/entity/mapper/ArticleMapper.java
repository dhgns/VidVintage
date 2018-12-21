package com.example.dhernandez.vidvintage.entity.mapper;

import com.example.dhernandez.vidvintage.entity.ArticleVO;
import com.example.dhernandez.vidvintage.entity.DAO.ArticleDAO;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by dhernandez on 19/12/2018.
 */

public class ArticleMapper {



    public static ArticleVO mapperDAOtoVO(ArticleDAO articleDAO){
        if(articleDAO == null)
            return null;

        ArticleVO articleVO = new ArticleVO();

        if(articleDAO.getUrl() != null)
            articleVO.setUrl(articleDAO.getUrl());
        if(articleDAO.getArticleImageURL() != null)
            articleVO.setArticleImageURL(articleDAO.getArticleImageURL());
        if(articleDAO.getAuthor() != null)
            articleVO.setAuthor(articleDAO.getAuthor());
        if(articleDAO.getDescription() != null)
            articleVO.setDescription(articleDAO.getDescription());
        if(articleDAO.getTitle() != null)
            articleVO.setTitle(articleDAO.getTitle());

        return articleVO;
    }


    public static ArticleDAO mapperVOtoDAO(ArticleVO articleVO) {
        ArticleDAO articleDAO = new ArticleDAO();

        if(articleVO.getUrl() != null)
            articleDAO.setUrl(articleVO.getUrl());
        if(articleVO.getTitle() != null)
            articleDAO.setTitle(articleVO.getTitle());
        if(articleVO.getArticleImageURL() != null)
            articleDAO.setArticleImageURL(articleVO.getArticleImageURL());
        if(articleVO.getAuthor() != null)
            articleDAO.setAuthor(articleVO.getAuthor());
        if(articleVO.getDescription() != null)
            articleDAO.setDescription(articleVO.getDescription());

        return articleDAO;
    }

    public static List<ArticleVO> mapperDAOtoVO(RealmResults<ArticleDAO> results) {
        List<ArticleVO> articleVOList = new ArrayList<>();
        ArticleVO value;
        for(ArticleDAO result : results){
            value = mapperDAOtoVO(result);
            articleVOList.add(value);
        }

        return articleVOList;
    }
}
