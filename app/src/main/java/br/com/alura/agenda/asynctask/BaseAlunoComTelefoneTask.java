package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.TipoTelefone;

public abstract class BaseAlunoComTelefoneTask extends AsyncTask<Void, Void, Void> {

    protected FinalizadaListener listener;

    protected void vinculaAlunoId(int alunoId, Telefone... telefones) {
        for (Telefone telefone : telefones) {
            telefone.setAlunoId ( alunoId );
        }
    }

    protected void atualizaIdDosTelefones(Telefone telefoneFixo, Telefone telefoneCelular, List<Telefone> telefones) {
        for (Telefone telefone : telefones) {
            if (telefone.getTipo () == TipoTelefone.FIXO) {
                telefoneFixo.setId ( telefone.getId () );
            } else if (telefone.getTipo () == TipoTelefone.CELULAR) {
                telefoneCelular.setId ( telefone.getId () );
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute ( aVoid );
        listener.quandoFinalizada ();
    }

    public interface FinalizadaListener {
        void quandoFinalizada();
    }
}
