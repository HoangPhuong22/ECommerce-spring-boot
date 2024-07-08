package vn.zerocoder.Mart.service;

import vn.zerocoder.Mart.model.Favourite;

import java.util.List;

public interface FavouriteService {
    String toggleFavourite(Long productId);
    List<Favourite> findAllFavouriteByUserName();
}
