package vn.zerocoder.Mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.zerocoder.Mart.model.Favourite;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    Favourite findByProductIdAndUserId(Long productId, Long userId);

    @Query("SELECT f FROM Favourite f WHERE f.user.username = ?1")
    List<Favourite> findAllFavouriteByUserDetail(String userName);
}
