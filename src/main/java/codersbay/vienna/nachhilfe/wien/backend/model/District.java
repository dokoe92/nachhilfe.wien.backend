package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public enum District {
    DISTRICT_1010("1010", "Innere Stadt"),
    DISTRICT_1020("1020", "Leopoldstadt"),
    DISTRICT_1030("1030", "Landstraße"),
    DISTRICT_1040("1040", "Wieden"),
    DISTRICT_1050("1050", "Margareten"),
    DISTRICT_1060("1060", "Mariahilf"),
    DISTRICT_1070("1070", "Neubau"),
    DISTRICT_1080("1080", "Josefstadt"),
    DISTRICT_1090("1090", "Alsergrund"),
    DISTRICT_1100("1100", "Favoriten"),
    DISTRICT_1110("1110", "Simmering"),
    DISTRICT_1120("1120", "Meidling"),
    DISTRICT_1130("1130", "Hietzing"),
    DISTRICT_1140("1140", "Penzing"),
    DISTRICT_1150("1150", "Rudolfsheim-Fünfhaus"),
    DISTRICT_1160("1160", "Ottakring"),
    DISTRICT_1170("1170", "Hernals"),
    DISTRICT_1180("1180", "Währing"),
    DISTRICT_1190("1190", "Döbling"),
    DISTRICT_1200("1200", "Brigittenau"),
    DISTRICT_1210("1210", "Floridsdorf"),
    DISTRICT_1220("1220", "Donaustadt"),
    DISTRICT_1230("1230", "Liesing");

    private final String postCode;
    private final String name;

    District(String postCode, String name) {
        this.postCode = postCode;
        this.name = name;
    }


}

