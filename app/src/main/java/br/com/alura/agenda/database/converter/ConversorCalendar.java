package br.com.alura.agenda.database.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class ConversorCalendar {

    @TypeConverter
    public Long toLong(Calendar source) {
        return source != null ? source.getTimeInMillis () : 0L;
    }

    @TypeConverter
    public Calendar toCalendar(Long source) {
        Calendar c = Calendar.getInstance ();
        if (source != null)
            c.setTimeInMillis ( source );
        return c;
    }
}
