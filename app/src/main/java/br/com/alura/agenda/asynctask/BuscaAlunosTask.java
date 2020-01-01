package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

public class BuscaAlunosTask extends AsyncTask<Void, Void, List<Aluno>> {

    private final ListaAlunosAdapter adapter;
    private final AlunoDAO dao;

    public BuscaAlunosTask(ListaAlunosAdapter adapter, AlunoDAO dao) {
        this.adapter = adapter;
        this.dao = dao;
    }

    @Override
    protected List<Aluno> doInBackground(Void... voids) {
        return dao.todos ();
    }

    @Override
    protected void onPostExecute(List<Aluno> alunos) {
        adapter.atualiza ( alunos );

        super.onPostExecute ( alunos );
    }
}
