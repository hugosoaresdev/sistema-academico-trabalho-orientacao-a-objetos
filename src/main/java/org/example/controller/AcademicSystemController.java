package org.example.controller;

import javafx.stage.Stage;
import org.example.app.LoginMenuApp;
import org.example.security.Authenticate;
import org.example.security.AuthenticationExceptionAcademic;
import org.example.security.User;
import org.example.service.AssessmentService;
import org.example.sistema.AcademicSystem;
import java.util.List;
import org.example.service.ReportService;
import org.example.service.PersistenceService;
import org.example.repository.PersistenceType;
import org.example.domain.Classroom;
import org.example.service.ClassService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AcademicSystemController {

    private final Stage stage;
    private final ReportService reportService = new ReportService();
    private Authenticate authenticate;
    private static final Logger logger = LoggerFactory.getLogger(AcademicSystemController.class);
    private final PersistenceService persistenceService = new PersistenceService();
    private final ClassService classService = new ClassService();
    private final AssessmentService assessmentService = new AssessmentService();
    // TUS-2413 (AC5): a tela de visualização pega os dados por aqui,
    // em vez de acessar o AcademicSystem diretamente.
    public List<Classroom> listarTurmas() {
        return AcademicSystem.getInstance().getClassrooms();
    }

    public AcademicSystemController(Stage stage){
        this.stage = stage;
        this.authenticate = new Authenticate();
    }

    // O metodo iniciar agora não tem while loop. Ele só joga a tela de login na janela.
    public void iniciar(){
        System.out.println("Iniciando interface gráfica...");
        mostrarTelaLogin();
    }

    // Metodo para exibir a tela de login
    public void mostrarTelaLogin() {
        LoginMenuApp loginView = new LoginMenuApp(stage, this);
        loginView.exibir();
    }


    public User processarLogin(String email, String senha) {
        try{
            User user = authenticate.login(email, senha);
            System.out.println("Bem-vindo, " + user.getUsername() + " [" + user.getRole() + "]");

            // Em vez de retornar para o while, nós já mandamos direcionar para o menu aqui!
            direcionarParaMenu(user);
            return user;

        } catch (AuthenticationExceptionAcademic e) {
            // Vamos reolançar a exceção ou tratar para a tela mostrar o erro em texto vermelho
            throw new RuntimeException("Falha no login: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo: " + e.getMessage());
    }
}

    public void direcionarParaMenu(User currentUser){
        currentUser.getMenuApp().carregarMenu(this.stage,this);
    }
    // TUS-2409 (AC4): a tela JavaFX delega o registro de turma para cá,
    // e o controller delega para o ClassService. A GUI não registra nada
    // diretamente (AC5). Deixamos a AcademicSystemException subir para a
    // tela poder mostrar o erro de validação.
    public void registrarTurma(Classroom classroom) {
        classService.registerClassroom(classroom);
    }
    // TUS-2410 (AC6): a tela JavaFX delega o registro da avaliação para cá,
// e o controller delega ao AssessmentService (AC7 - GUI sem regra de negócio).
    public void registrarAvaliacao(Long classroomID, int tipo, double value, double weight) {
        assessmentService.registerAssessment(classroomID, tipo, value, weight);
    }
    // TUS-2411 (AC6): a tela delega a geração dos relatórios para cá,
// e o controller delega ao ReportService.
    public String gerarRelatorioResumo() {
        return reportService.generateClassAssessmentSummaryReport();
    }
    public String gerarRelatorioPeso() {
        return reportService.generateAssessmentWeightReport();
    }
    public String gerarRelatorioPersistencia() {
        return reportService.generatePersistenceConfigurationReport();
    }
    // TUS-2412 (AC5): a tela delega a configuração para cá,
    // e o controller delega ao PersistenceService.
    public void configurarPersistencia(PersistenceType tipo) {
        persistenceService.setPersistenceType(tipo);
    }
    public PersistenceType getPersistenceType() {
        return persistenceService.getPersistenceType();
    }
    // Save Data: delega ao PersistenceService, que salva no formato configurado.
    // O log da operação já é feito dentro do repositório (TUS-2393).
    public void salvarDados() throws IOException {
        persistenceService.save();
    }

    // US-2379: encerra a sessão atual e volta para a tela de login.
    public void logout(User currentUser) {
        // AC4: registra o logout para auditoria (TUS-2391), com usuário e função.
        logger.info("Logout realizado | usuário={} | função={}",
                currentUser.getUsername(), currentUser.getRole());

        // AC1/AC2/AC6/AC7: troca a cena de volta para o login. O objeto do usuário
        // deixa de ser referenciado, então os privilégios só voltam após nova
        // autenticação (AC3). Os dados acadêmicos no Singleton ficam intactos (AC5).
        mostrarTelaLogin();
    }

}


