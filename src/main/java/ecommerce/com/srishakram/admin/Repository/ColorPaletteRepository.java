package ecommerce.com.srishakram.admin.Repository;

import ecommerce.com.srishakram.models.ColorPalette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorPaletteRepository  extends JpaRepository<ColorPalette,Long> {
    Optional<ColorPalette> findByMainIgnoreCase(String main);
    Optional<ColorPalette> findById(Long id);
    ColorPalette findFirstByOrderByIdAsc();

    @Query("SELECT c.main FROM ColorPalette c")
    List<String> findAllMainColors();

    List<ColorPalette> findByMainInIgnoreCase(List<String> main);
}
