package com.iquinto.workingstudent.model.enums;


import java.io.Serializable;

public enum Province{

    ALAVA ("Alava"),
    ALBACETE("Albacete"),
    ALICANTE("Alicante"),
    ALMERÍA("Almería"),
    ASTURIAS("Asturias"),
    AVILA("Avila"),
    BADAJOZ("Badajoz"),
    BARCELONA("Barcelona"),
    BURGOS("Burgos"),
    CÁCERES("Cáceres"),
    CÁDIZ("Cádiz"),
    CANTABRIA("Cantabria"),
    CASTELLÓN("Castellón"),
    CIUDAD_REAL("Ciudad Real"),
    CÓRDOBA("Córdoba"),
    LA_CORUÑA("La Coruña"),
    CUENCA("Cuenca"),
    GERONA("Gerona"),
    GRANADA("Granada"),
    GUADALAJARA("Guadalajara"),
    GUIPÚZCOA("Guipúzcoa"),
    HUELVA("Huelva"),
    HUESCA("Huesca"),
    ISLAS_BALEARES("Islas Baleares"),
    JAÉN("Jaén"),
    LEÓN("León"),
    LÉRIDA("Lérida"),
    LUGO("Lugo"),
    MADRID("Madrid"),
    MÁLAGA("Málaga"),
    MURCIA("Murcia"),
    NAVARRA("Navarra"),
    ORENSE("Orense"),
    PALENCIA("Palencia"),
    LAS_PALMAS("Las Palmas"),
    PONTEVEDRA("Pontevedra"),
    LA_RIOJA("La Rioja"),
    SALAMANCA("Salamanca"),
    SEGOVIA("Segovia"),
    SEVILLA("Sevilla"),
    SORIA("Soria"),
    TARRAGONA("Tarragona"),
    SANTA_CRUZ_DE_TENERIFE("Santa Cruz de Tenerife"),
    TERUEL("Teruel"),
    TOLEDO("Toledo"),
    VALENCIA("Valencia"),
    VALLADOLID("Valladolid"),
    VIZCAYA("Vizcaya"),
    ZAMORA("Zamora"),
    ZARAGOZA("Zaragoza");

    private String text;
    Province(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }


    public static Province fromString(String text) {
        for (Province province : Province.values()) {
            if (province.text.equalsIgnoreCase(text)) {
                return province;
            }
        }
        return null;
    }

}
