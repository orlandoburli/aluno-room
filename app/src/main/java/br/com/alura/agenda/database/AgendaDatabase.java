package br.com.alura.agenda.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import br.com.alura.agenda.database.converter.ConversorCalendar;
import br.com.alura.agenda.database.converter.ConversorTipoTelefone;
import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

import static br.com.alura.agenda.database.migrations.AgendaMigrations.ALL_MIGRATIONS;

@Database(entities = {Aluno.class, Telefone.class}, version = 6, exportSchema = false)
@TypeConverters({ConversorCalendar.class, ConversorTipoTelefone.class})
public abstract class AgendaDatabase extends RoomDatabase {

    public static final String AGENDA_DB = "agenda.db";

    public static AgendaDatabase getInstance(Context context) {
        return Room
                .databaseBuilder ( context, AgendaDatabase.class, AGENDA_DB )
                .addMigrations ( ALL_MIGRATIONS )
                .build ();
    }

    public abstract AlunoDAO getRoomAlunoDAO();

    public abstract TelefoneDAO getTelefoneDAO();

}