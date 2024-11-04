package fr.nkri.api.utils.times;

import java.util.HashMap;

public enum TimeUtils {

	SECONDE("Seconde(s)", "sec", 1L),
	MINUTE("Minute(s)", "min", 60L),
	HEURE("Heure(s)", "h", 3600L),
	JOUR("Jour(s)", "j", 86400L),
	MOIS("Mois", "m", 2592000L),
	;
	
    private final String name;
    private final String shortcut;
    private final long toSecond;

    private static HashMap<String, TimeUtils> idShortcuts = new HashMap<>();

    TimeUtils(final String name, final String shortcut, final long toSecond) {
        this.name = name;
        this.shortcut = shortcut;
        this.toSecond = toSecond;
    }
    static {
        for(TimeUtils units : values()){
        	idShortcuts.put(units.shortcut, units);
        }
    }

    //Retour un élément de l'énumératione en fonction du paramètre
    public static TimeUtils getFromShortcut(final String shortcut){
        return idShortcuts.get(shortcut);
    }
    
    //vérifie si le paramètre existe ou non
    public static boolean existFromShortcut(final String shortcut){
        return idShortcuts.containsKey(shortcut);
    }

    /*@Getter*/
    public String getName(){
        return name;
    }

    public String getShortcut(){
        return shortcut;
    }

    public long getToSecond() {
        return toSecond;
    }
}