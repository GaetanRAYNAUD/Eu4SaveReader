package eu4SaveReader.Utils;

public enum Govs {

    SYNTH_GOV_1("synthetic_nation", 1, "Machine Intelligence", "Intelligence artificielle"),
    SYNTH_GOV_2("synthetic_nation", 2, "Machine Intelligence", "Intelligence artificielle"),
    SYNTH_GOV_3("synthetic_nation", 3, "Machine Intelligence", "Intelligence artificielle"),
    HORDE("yuan_empire", 1, "Machine Intelligence", "Intelligence artificielle"),
    KHANATE("yuan_empire", 2, "Machine Intelligence", "Intelligence artificielle"),
    KHAGANATE("yuan_empire", 3, "Machine Intelligence", "Intelligence artificielle"),
    ILKHANATE("ilkhanate_march", 1, "Machine Intelligence", "Intelligence artificielle"),
    PARLIAMENT_OF_HEAVEN("celestial_parliament", 3, "Machine Intelligence", "Intelligence artificielle"),
    REVOLUTIONARY_PEASANT_REPUBLIC("revolutionary_peasant_republic", 3, "Machine Intelligence", "Intelligence artificielle"),
    PASHALIK("ottoman_marches", 1, "Machine Intelligence", "Intelligence artificielle"),
    BEYLIK("ottoman_vassals", 1, "Machine Intelligence", "Intelligence artificielle"),
    MARCH("march_christian_monarchy", 1, "Machine Intelligence", "Intelligence artificielle"),
    VICEROYALTY("march_christian_monarchy", 2, "Machine Intelligence", "Intelligence artificielle"),
    EMPIRE("march_christian_monarchy", 3, "Machine Intelligence", "Intelligence artificielle"),
    DUCHY("holy_roman_electors_monarchy", 1, "Machine Intelligence", "Intelligence artificielle"),
    KINGDOM("holy_roman_electors_monarchy", 2, "Machine Intelligence", "Intelligence artificielle"),
    EMPIRE_HRE("holy_roman_electors_monarchy", 3, "Machine Intelligence", "Intelligence artificielle"),
    BISHOPRIC("holy_roman_electors_bishoprics", 1, "Machine Intelligence", "Intelligence artificielle"),
    ARCHBISHOPRIC("holy_roman_electors_bishoprics", 2, "Machine Intelligence", "Intelligence artificielle"),
    PATRIARCHATE("holy_roman_electors_bishoprics", 3, "Machine Intelligence", "Intelligence artificielle"),
    DAIMYO("japanese_shogunate", 1, "Machine Intelligence", "Intelligence artificielle"),
    KINGDOM_JAP("japanese_shogunate", 2, "Machine Intelligence", "Intelligence artificielle"),
    EMPIRE_JAP("japanese_shogunate", 3, "Machine Intelligence", "Intelligence artificielle"),
    MONASTIC_ORDER("teutonic_monastic_order", 1, "Machine Intelligence", "Intelligence artificielle"),
    MONASTIC_ORDER_LIV("livonian_monastic_order", 1, "Machine Intelligence", "Intelligence artificielle"),
    MONASTIC_ORDER_GER("germanic_monastic_order", 1, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_PRINCIPALITY("jewish_theocracy", 1, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_STATE("jewish_theocracy", 2, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_EMPIRE("jewish_theocracy", 3, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_PRINCIPALITY_ZOR("zoroastrian_theocracy", 1, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_STATE_ZOR("zoroastrian_theocracy", 2, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_EMPIRE_ZOR("zoroastrian_theocracy", 3, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_STATE_SIKH("sikh_theocracy", 1, "Machine Intelligence", "Intelligence artificielle"),
    MISL("sikh_theocracy", 2, "Machine Intelligence", "Intelligence artificielle"),
    CONFEDERATION("sikh_theocracy", 3, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_PRINCIPALITY_HINDU("hindu_theocracy", 1, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_STATE_HINDU("hindu_theocracy", 2, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_EMPIRE_HINDU("hindu_theocracy", 3, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_PRINCIPALITY_SHINTO("shinto_theocracy", 1, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_STATE_SHINTO("shinto_theocracy", 2, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_EMPIRE_SHINTO("shinto_theocracy", 3, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_PRINCIPALITY_BUD("buddhist_theocracy", 1, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_STATE_BUD("buddhist_theocracy", 2, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_EMPIRE_BUD("buddhist_theocracy", 3, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_PRINCIPALITY_CONF("confucian_theocracy", 1, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_STATE_CONF("confucian_theocracy", 2, "Machine Intelligence", "Intelligence artificielle"),
    HOLY_EMPIRE_CONF("confucian_theocracy", 3, "Machine Intelligence", "Intelligence artificielle"),
    IMAMATE("ibadi_theocracy", 1, "Machine Intelligence", "Intelligence artificielle"),
    GREAT_IMAMATE("ibadi_theocracy", 2, "Machine Intelligence", "Intelligence artificielle"),
    EMPIRE_IBADI("ibadi_theocracy", 3, "Machine Intelligence", "Intelligence artificielle");

    private String code;

    private int rank;

    private String eng;

    private String fr;

    Govs (String code, int rank, String eng, String fr) {
        this.code = code;
        this.rank = rank;
        this.eng = eng;
        this.fr = fr;
    }

    public String getCode () {
        return code;
    }

    public void setCode (String code) {
        this.code = code;
    }

    public int getRank () {
        return rank;
    }

    public void setRank (int rank) {
        this.rank = rank;
    }

    public String getEng () {
        return eng;
    }

    public void setEng (String eng) {
        this.eng = eng;
    }

    public String getFr () {
        return fr;
    }

    public void setFr (String fr) {
        this.fr = fr;
    }
}
