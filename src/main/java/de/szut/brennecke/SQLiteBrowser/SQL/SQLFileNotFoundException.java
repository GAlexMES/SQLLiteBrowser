package de.szut.brennecke.SQLiteBrowser.SQL;

/**
 * This class is a Exception, which should be thrown, when a database file could not be founded.
 * @author Alexander Brennecke
 *
 */
@SuppressWarnings("serial")
public class SQLFileNotFoundException extends Exception
{
	public SQLFileNotFoundException(){
        super("Dateipfad konnte nicht gefunden werden. Datenbank wird entfernt");
    }
}
