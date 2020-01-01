package br.com.alura.agenda.database.converter;

import android.arch.persistence.room.TypeConverter;

import br.com.alura.agenda.model.TipoTelefone;

public class ConversorTipoTelefone {

    @TypeConverter
    public String paraString(TipoTelefone source) {
        return source != null ? source.name () : null;
    }

    @TypeConverter
    public TipoTelefone paraTipoTelefone(String source) {
        return source != null ? TipoTelefone.valueOf ( source ) : TipoTelefone.FIXO;
    }
}
