package com.example.dhernandez.vidvintage.entity.mapper;

import com.example.dhernandez.vidvintage.entity.Author;
import com.example.dhernandez.vidvintage.entity.DAO.CocktailAuthorDAO;

/**
 * Created by dhernandez on 08/12/2018.
 */

public class AuthorMapper {

    public static CocktailAuthorDAO mapperVOtoDAO(Author author) {
        CocktailAuthorDAO authorDAO = new CocktailAuthorDAO();

        authorDAO.setEmail(author.getEmail());
        authorDAO.setLastName(author.getLastName());
        authorDAO.setName(author.getName());
        authorDAO.setUsername(author.getUsername());

        return authorDAO;

    }

    public static Author mapperDAOtoVO(CocktailAuthorDAO authorDAO) {
        Author author = new Author();

        author.setUsername(authorDAO.getUsername());
        author.setLastName(authorDAO.getLastName());
        author.setEmail(authorDAO.getUsername());
        author.setName(authorDAO.getName());

        return author;
    }
}
