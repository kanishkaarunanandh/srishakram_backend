package ecommerce.com.srishakram.admin.Controllers;


import ecommerce.com.srishakram.admin.Repository.ColorPaletteRepository;
import ecommerce.com.srishakram.admin.Service.ColorPaletteService;
import ecommerce.com.srishakram.models.ColorPalette;
import ecommerce.com.srishakram.models.CustomerSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colorpattern")
public class ColorPaletteController {

    @Autowired
    private ColorPaletteService colorPaletteService;
    @Autowired
    private ColorPaletteRepository ColorPaletterepo;

    //single
    @PostMapping("store/colors")
    public ColorPalette saveColorPalette(@RequestBody ColorPalette colorpalette) {
        return colorPaletteService.saveSelection(colorpalette);
    }
    //batch
    @PostMapping("/color-palette/batch")
    public ResponseEntity<?> addColorPalettes(@RequestBody List<ColorPalette> palettes) {
        List<ColorPalette> saved = ColorPaletterepo.saveAll(palettes);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("getby/color/{main}")
    public ColorPalette getCustomerSelection(@PathVariable String main) {
        return colorPaletteService.getByMain(main);
    }

    //get multiple color
    @GetMapping("getby/color")
    public List<ColorPalette> getCustomerSelection(
            @RequestParam List<String> main
    ) {
        return colorPaletteService.getByMainList(main);
    }


    @GetMapping("getby/id/{id}")
    public ColorPalette getCustomerSelection(@PathVariable Long id) {
        return colorPaletteService.getById(id);
    }

    @GetMapping("/color-palette/first")
    public ResponseEntity<ColorPalette> getFirstColorPalette() {
        ColorPalette firstPalette = colorPaletteService.getFirstColorPalette();
        return ResponseEntity.ok(firstPalette);
    }

    @GetMapping("/all-main-colors")
    public ResponseEntity<List<String>> getAllMainColors() {
        List<String> colors = colorPaletteService.getAllMainColors();
        return ResponseEntity.ok(colors);
    }

}
