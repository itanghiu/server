package com.foryousoft.hsking.enums;

import java.util.Locale;

/**
 * User: i-tang HIU
 * Date: 10 mars 2011
 * Time: 21:45:19
 */
public enum LanguageType {

    ENGLISH(0L, "EN", "English", Locale.ENGLISH),
    FRENCH(1L, "FR", "French", Locale.FRENCH),
    SPANISH(2L, "ES", "Spanish", new Locale("es"));
   /* GERMAN("DE", "German", Locale.GERMAN),
    CHINESE("CN", "Chinese", Locale.CHINESE),
    ,
    ITALIAN("IT", "Italian", Locale.ITALIAN);*/

    private Long id;
    private String shortName;
    private String languageLabel;
    private Locale local;

    private LanguageType(Long _id, String _locale, String _languageLabel, Locale _local) {

        id = _id;
        shortName = _locale;
        languageLabel = _languageLabel;
        local = _local;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locale getLocale() {
        return local;
    }

    public void setLocale(Locale local) {
        this.local = local;
    }

    public String getShortName() {
        return shortName.toUpperCase();
    }

    public String getLanguageLabel() {
        return languageLabel;
    }



    public static LanguageType getLanguageType(String _locale) {

        LanguageType[] languageTypes = LanguageType.values();
        for (LanguageType languageType : languageTypes) {
            String locale = languageType.getShortName();
            if (locale.equals(_locale.toUpperCase())) return languageType;
        }
        return ENGLISH;
    }
}
