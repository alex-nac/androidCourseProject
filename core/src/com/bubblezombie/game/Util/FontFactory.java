package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;

public class FontFactory implements Disposable {
    private FreeTypeFontGenerator _generator;

    public FontFactory(String path) {
        _generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
    }

    /**
     * @param type font style determined in FontType enum
     * @param size size of the font to be returned in pixels
     * @return a raster generated font from a vector-font
     */
    public BitmapFont getFont(FontType type, int size) {
        FreeTypeFontParameter param = type.getParam();
        param.size = size;
        return _generator.generateFont(param);
    }

    @Override
    public void dispose() {
        _generator.dispose();
    }

    public enum FontType {
        DEFAULT, HEADER, BUTTON;

        private final FreeTypeFontParameter _param;

        FontType() {
            if (this.name().equals("DEFAULT")) {
                _param = new FreeTypeFontParameter();
            } else if (this.name().equals("HEADER")) {
                _param = new FreeTypeFontParameter();
            } else if (this.name().equals("BUTTON")) {
                _param = new FreeTypeFontParameter();
            } else {
                _param = null;
            }
        }

        public FreeTypeFontParameter getParam() {
            return _param;
        }
    }
}
