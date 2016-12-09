package com.foryousoft.hsking.enums;

/**
 * Created with IntelliJ IDEA.
 * User: i-tang
 * Date: 18/02/13
 * Time: 15:28
 */
public enum Software {

    NULL(0L, ""),
    MCFC(1L, "MyChineseFlashCards"),
    MCW(2L, "MyChineseWords");

    private Long id;
    private String name;

    Software(Long _id, String _name) {

        id = _id;
        name = _name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Software getSoftware(String _software) {

        Software[] softArray = values();
        for (Software soft : softArray) {
            if (soft.toString().toUpperCase().equals(_software.toUpperCase())) {
                return soft;
            }
        }
        return null;
    }

    public static Software getSoftware(Long _code) {

        Software[] softArray = values();
        for (Software soft : softArray) {
            if (soft.getId() == _code) {
                return soft;
            }
        }
        return null;
    }
}

