package br.com.alura.agenda.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import br.com.alura.agenda.database.converter.ConversorCalendar;
import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

import static br.com.alura.agenda.database.migrations.AgendaMigrations.ALL_MIGRATIONS;

@Database(entities = {Aluno.class}, version = 4, exportSchema = false)
@TypeConverters({ConversorCalendar.class})
public abstract class AgendaDatabase extends RoomDatabase {

    public static final String AGENDA_DB = "agenda.db";


    public static AgendaDatabase getInstance(Context context) {
        return Room
                .databaseBuilder ( context, AgendaDatabase.class, AGENDA_DB )
                .allowMainThreadQueries ()
                .addMigrations ( ALL_MIGRATIONS )
                .build ();
    }

    public abstract AlunoDAO getRoomAlunoDAO();
}