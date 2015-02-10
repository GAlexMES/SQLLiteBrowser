package de.szut.brennecke.SQLiteBrowser.SQL;

@SuppressWarnings("serial")
public class SQLFileNotFoundException extends Exception
{
	public SQLFileNotFoundException(){
        super("Dateipfad konnte nicht gefunden werden. Datenbank wird entfernt");
    }
}
