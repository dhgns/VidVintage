package com.example.dhernandez.vidvintage.entity.mapper;

import android.graphics.Bitmap;
import android.util.Base64;

import com.example.dhernandez.vidvintage.Utils.ImageUtil;
import com.example.dhernandez.vidvintage.entity.DAO.PictureProfileDAO;

import java.io.ByteArrayOutputStream;

/**
 * Created by dhernandez on 26/12/2018.
 */

public class PictureProfileMapper {
    public static PictureProfileDAO mapperVOtoDAO(String email, Bitmap bitmap) {
        PictureProfileDAO pictureProfileDAO = new PictureProfileDAO();

        pictureProfileDAO.setId(email);
        pictureProfileDAO.setData(ImageUtil.convert(bitmap));
        return pictureProfileDAO;
    }

    public static Bitmap mapperDAOtoVO(PictureProfileDAO pictureProfileDAO) {
        if (pictureProfileDAO == null)
            return null;
        return ImageUtil.convert(pictureProfileDAO.getData());
    }
}
