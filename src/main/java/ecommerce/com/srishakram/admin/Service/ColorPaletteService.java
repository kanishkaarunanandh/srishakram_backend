package ecommerce.com.srishakram.admin.Service;


import ecommerce.com.srishakram.admin.Repository.ColorPaletteRepository;
import ecommerce.com.srishakram.models.ColorPalette;
import ecommerce.com.srishakram.models.CustomerSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorPaletteService {

    @Autowired
    private ColorPaletteRepository ColorPaletterepo;

    public ColorPalette saveSelection(ColorPalette colorpalette) {
        return ColorPaletterepo.save(colorpalette);
    }

    //get single color
    public ColorPalette getByMain(String main) {
        return ColorPaletterepo.findByMainIgnoreCase(main)
                .orElseThrow(() -> new RuntimeException("Color not found: " + main));
    }

    public List<ColorPalette> getByMainList(List<String> mainList) {
        return ColorPaletterepo.findByMainInIgnoreCase(mainList);
    }

    public ColorPalette getById(Long id) {
        return ColorPaletterepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Id not found: " + id));
    }

    public ColorPalette getFirstColorPalette() {
        return ColorPaletterepo.findFirstByOrderByIdAsc();
    }

    public List<String> getAllMainColors() {
        return ColorPaletterepo.findAllMainColors();
    }
}
