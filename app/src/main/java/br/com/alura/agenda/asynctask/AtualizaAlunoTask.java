package br.com.alura.agenda.asynctask;

import java.util.List;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class AtualizaAlunoTask extends BaseAlunoComTelefoneTask {

    private final AlunoDAO alunoDAO;
    private final Aluno aluno;
    private final TelefoneDAO telefoneDAO;
    private final Telefone telefoneFixo;
    private final Telefone telefoneCelular;
    private List<Telefone> telefones;

    public AtualizaAlunoTask(AlunoDAO alunoDAO,
                             Aluno aluno,
                             TelefoneDAO telefoneDAO,
                             Telefone telefoneFixo,
                             Telefone telefoneCelular,
                             List<Telefone> telefones,
                             FinalizadaListener listener) {
        this.alunoDAO = alunoDAO;
        this.aluno = aluno;
        this.telefoneDAO = telefoneDAO;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.telefones = telefones;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        alunoDAO.edita ( aluno );

        int alunoId = aluno.getId ();

        atualizaIdDosTelefones ( telefoneFixo, telefoneCelular, telefones );

        vinculaAlunoId ( alunoId, telefoneFixo, telefoneCelular );

        telefoneDAO.atualiza ( telefoneFixo, telefoneCelular );

        return null;
    }
}
