package br.com.alura.agenda.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.alura.agenda.model.Telefone;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TelefoneDAO {

    @Query("SELECT t.* FROM `Telefone` t " +
            "  WHERE t.alunoId = :idAluno" +
            "  LIMIT 1")
    Telefone buscaPrimeiroTelefoneDoAluno(int idAluno);

    @Insert
    void salva(Telefone... telefones);

    @Query("SELECT t.* FROM `Telefone` t " +
            "  WHERE t.alunoId = :idAluno")
    List<Telefone> buscaTodosTelefonesDoAluno(int idAluno);

    @Update
    void atualiza(List<Telefone> telefones);

    @Insert(onConflict = REPLACE)
    void atualiza(Telefone ... telefones);
}