package com.res;

import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import com.skcs.kiosk.Kiosk;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by adeirwansiah on 11/10/16.
 */
public class Resource {

    static int language = 0;

    private static ResourceBundle resLabel;
    private static ResourceBundle resConfig;
    private static List<SVGGlyph> glyphs;
    private static Set<String> glyphNames;
    private static HashMap<String, Integer>iconGlyphs;
    public static void init() {
        resLabel = ResourceBundle.getBundle("Label");
        new Thread(()->{
            try {
                SVGGlyphLoader.loadGlyphsFont(Kiosk.class.getResourceAsStream("/resources/font/ionicons.svg"), "");
                glyphs = SVGGlyphLoader
                    .getAllGlyphsIDs()
                    .stream()
                    .map(item -> SVGGlyphLoader.getGlyph(item))
                    .collect(Collectors.toList());
                Collections.sort(glyphs, (o1,o2)-> o1.getName().compareTo(o2.getName()));

                iconGlyphs = new HashMap<String, Integer>(glyphs.size());
                Iterator<SVGGlyph> iter = glyphs.iterator();
                int i = 0;
                while(iter.hasNext()) {
                    SVGGlyph glyph = iter.next();
                    iconGlyphs.put(glyph.getName(), i++);
                    //System.out.println(glyph.getName());
                }
                //System.out.println("size=" + glyphs.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        resConfig = ResourceBundle.getBundle("Config");
    }

    public static SVGGlyph getGlyph(String name) {
        if (iconGlyphs.containsKey(name)) {
            int idx = iconGlyphs.get(name);
            SVGGlyph g = glyphs.get(idx);
            String s = g.getShape().toString();
            int st = s.indexOf("\"", 0);
            int en = s.indexOf("\"", st + 1);
            String path = s.substring(st + 1, en);
            SVGGlyph ret = new SVGGlyph(g.getGlyphId(), g.getName(), path, g.getFill());
            return ret;
        }
        return null;
    }

    public static String getLabel(String key) {
        String[] ret = resLabel.getString(key).split("~");
        if (ret.length > 1) {
            return ret[language];
        }
        return ret[0];
    }

    public static void setLanguage(int language_) {
        language = language_;
    }

    public static String getConfig(String key) {
        String ret = "";
        if (resConfig.containsKey(key)) {
            ret = resConfig.getString(key);
        }
        return ret;
    }

    public static int getConfigInt(String key) {
        int ret = -1;
        if (resConfig.containsKey(key)) {
            ret = Integer.parseInt(resConfig.getString(key));
        }
        return ret;
    }
}
