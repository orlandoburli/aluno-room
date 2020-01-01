package br.com.alura.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.asynctask.AtualizaAlunoTask;
import br.com.alura.agenda.asynctask.BuscaTodosTelefonesDoAlunoTask;
import br.com.alura.agenda.asynctask.InsereAlunoTask;
import br.com.alura.agenda.database.AgendaDatabase;
import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.TipoTelefone;

import static br.com.alura.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefoneFixo;
    private EditText campoTelefoneCelular;
    private EditText campoEmail;
    private AlunoDAO alunoDAO;
    private TelefoneDAO telefoneDAO;
    private Aluno aluno;
    private List<Telefone> telefones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_formulario_aluno );

        this.alunoDAO = AgendaDatabase
                .getInstance ( this )
                .getRoomAlunoDAO ();

        this.telefoneDAO = AgendaDatabase
                .getInstance ( this )
                .getTelefoneDAO ();

        inicializacaoDosCampos ();

        carregaAluno ();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ()
                .inflate ( R.menu.activity_formulario_aluno_menu, menu );
        return super.onCreateOptionsMenu ( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId ();
        if (itemId == R.id.activity_formulario_aluno_menu_salvar) {
            finalizaFormulario ();
        }
        return super.onOptionsItemSelected ( item );
    }

    private void carregaAluno() {
        Intent dados = getIntent ();
        if (dados.hasExtra ( CHAVE_ALUNO )) {
            setTitle ( TITULO_APPBAR_EDITA_ALUNO );
            aluno = (Aluno) dados.getSerializableExtra ( CHAVE_ALUNO );
            preencheCampos ();
        } else {
            setTitle ( TITULO_APPBAR_NOVO_ALUNO );
            aluno = new Aluno ();
        }
    }

    private void preencheCampos() {
        campoNome.setText ( aluno.getNome () );
        campoEmail.setText ( aluno.getEmail () );

        preencheCamposTelefone ();
    }

    private void preencheCamposTelefone() {
        new BuscaTodosTelefonesDoAlunoTask ( telefoneDAO, aluno, telefones -> {
            this.telefones = telefones;

            for (Telefone telefone : telefones) {
                if (telefone.getTipo () == TipoTelefone.FIXO) {
                    campoTelefoneFixo.setText ( telefone.getNumero () );
                } else if (telefone.getTipo () == TipoTelefone.CELULAR) {
                    campoTelefoneCelular.setText ( telefone.getNumero () );
                }
            }
        } ).execute ();
    }

    private void finalizaFormulario() {
        preencheAluno ();

        String   numeroTelefoneFixo = campoTelefoneFixo.getText ().toString ();
        Telefone telefoneFixo       = criaTelefone ( numeroTelefoneFixo, TipoTelefone.FIXO );

        String   numeroTelefoneCelular = campoTelefoneCelular.getText ().toString ();
        Telefone telefoneCelular       = criaTelefone ( numeroTelefoneCelular, TipoTelefone.CELULAR );

        if (aluno.temIdValido ()) {
            atualizaAluno ( telefoneFixo, telefoneCelular );
        } else {
            insereAluno ( telefoneFixo, telefoneCelular );
        }
    }

    private Telefone criaTelefone(String numero, TipoTelefone tipoTelefone) {
        return new Telefone ( numero, tipoTelefone );
    }

    private void insereAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new InsereAlunoTask ( alunoDAO, telefoneDAO, aluno, telefoneFixo, telefoneCelular, this::finish ).execute ();
    }

    private void atualizaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new AtualizaAlunoTask ( alunoDAO, aluno, telefoneDAO, telefoneFixo, telefoneCelular, telefones, this::finish ).execute ();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById ( R.id.activity_formulario_aluno_nome );
        campoTelefoneFixo = findViewById ( R.id.activity_formulario_aluno_telefone_fixo );
        campoTelefoneCelular = findViewById ( R.id.activity_formulario_aluno_telefone_celular );
        campoEmail = findViewById ( R.id.activity_formulario_aluno_email );
    }

    private void preencheAluno() {
        String nome  = campoNome.getText ().toString ();
        String email = campoEmail.getText ().toString ();

        aluno.setNome ( nome );
        aluno.setEmail ( email );
    }
}
