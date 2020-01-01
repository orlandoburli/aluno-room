package br.com.alura.agenda.asynctask;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class InsereAlunoTask extends BaseAlunoComTelefoneTask {
    private final AlunoDAO alunoDAO;
    private final TelefoneDAO telefoneDAO;
    private Aluno aluno;
    private Telefone telefoneFixo;
    private Telefone telefoneCelular;
    private FinalizadaListener listener;

    public InsereAlunoTask(AlunoDAO alunoDAO,
                           TelefoneDAO telefoneDAO,
                           Aluno aluno,
                           Telefone telefoneFixo,
                           Telefone telefoneCelular,
                           FinalizadaListener listener) {
        this.alunoDAO = alunoDAO;
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Long alunoId = alunoDAO.salva ( aluno );

        vinculaAlunoId ( alunoId.intValue (), telefoneFixo, telefoneCelular );

        telefoneDAO.salva ( telefoneFixo, telefoneCelular );

        return null;
    }
}
