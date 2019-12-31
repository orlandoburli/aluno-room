package br.com.alura.agenda.database.migrations;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

public class AgendaMigrations {

    public static final Migration MIGRATION_1_2 = new Migration ( 1, 2 ) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL ( "ALTER TABLE `Aluno` ADD COLUMN sobrenome TEXT" );
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration ( 2, 3 ) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Criar nova tabela com as informações desejadas
            database.execSQL ( "CREATE TABLE IF NOT EXISTS `Aluno_novo` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nome` TEXT, " +
                    "`telefone` TEXT, " +
                    "`email` TEXT)" );

            // Copiar os dados da tabela antiga para a nova
            database.execSQL ( "INSERT INTO `Aluno_novo`(id, nome, telefone, email) SELECT id, nome, telefone, email FROM `Aluno`" );

            // Remover a tabela antiga
            database.execSQL ( "DROP TABLE `Aluno`" );

            // Remover a tabela nova para o nome da antiga
            database.execSQL ( "ALTER TABLE `Aluno_novo` RENAME TO `Aluno`" );
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration ( 3, 4 ) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL ( "ALTER TABLE `Aluno` ADD COLUMN `momentoDeCadastro` INTEGER" );
        }
    };

    public static final Migration[] ALL_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4};
}
