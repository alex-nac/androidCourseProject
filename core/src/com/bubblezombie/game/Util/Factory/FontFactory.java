package com.bubblezombie.game.Util.Factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.bubblezombie.game.BubbleZombieGame;

public class FontFactory {
    private static FreeTypeFontGenerator _EuropeExtGenerator;
    private static FreeTypeFontGenerator _SomeOtherFontGenerator;

    /**
     * initializes all fonts generators
     * should be invoked before all following methods
     * @param pathEuropeExt path to europe ext vector font
     * @param path path to other font
     */
    public static void initialize(String pathEuropeExt, String path) {
        _EuropeExtGenerator = new FreeTypeFontGenerator(Gdx.files.internal(pathEuropeExt));
        _SomeOtherFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(path));
    }

    /**
     * generates bitmap for europe ext font with given size
     * @param type font style determined in FontType enum
     * @param size size of the font to be returned in pixels
     * @return a raster generated font from a vector-font
     */
    public static BitmapFont getEuropeExt(FontType type, int size) {
        FreeTypeFontParameter param = type.getParam();
        param.size = size;
        return _EuropeExtGenerator.generateFont(param);
    }

    /**
     * generates bitmap for some other font with given size
     * @param type font style determined in FontType enum
     * @param size size of the font to be returned in pixels
     * @return a raster generated font from a vector-font
     */
    public static BitmapFont getSomeOther(FontType type, int size) {
        FreeTypeFontParameter param = type.getParam();
        param.size = size;
        return _EuropeExtGenerator.generateFont(param);
    }

    /**
     * decreases reference counter for all inner fields
     */
    public static void dispose() {
        _EuropeExtGenerator.dispose();
        _SomeOtherFontGenerator.dispose();
    }

    public enum FontType {
        DEFAULT, HEADER, BUTTON;

        private final FreeTypeFontParameter _param;

        FontType() {
            if (this.name().equals("DEFAULT")) {
                _param = new FreeTypeFontParameter();
            } else if (this.name().equals("HEADER")) {
                _param = new FreeTypeFontParameter();
                _param.color = new Color(12/255f, 11/255f, 80/255f, 1.0f);
            } else if (this.name().equals("BUTTON")) {
                _param = new FreeTypeFontParameter();
                _param.color = new Color(120/255f, 145/255f, 125/255f, 1.0f);
            } else {
                _param = null;
            }
        }

        public FreeTypeFontParameter getParam() {
            return _param;
        }
    }
}
