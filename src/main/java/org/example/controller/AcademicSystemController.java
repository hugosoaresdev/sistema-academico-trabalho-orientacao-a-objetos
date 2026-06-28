package org.example.controller;

import javafx.stage.Stage;
import org.example.app.LoginMenuApp;
import org.example.domain.Classroom;
import org.example.repository.PersistenceType;
import org.example.security.Authenticate;
import org.example.security.AuthenticationExceptionAcademic;
import org.example.security.User;
import org.example.service.AssessmentService;
import org.example.service.ClassService;
import org.example.service.PersistenceService;
import org.example.service.ReportService;
import org.example.sistema.AcademicSystem;

import java.io.IOException;
import java.util.List;

public class AcademicSystemController {

    private final Stage stage;

    private final Authenticate authenticate = new Authenticate();
    private final ReportService reportService = new ReportService();
    private final PersistenceService persistenceService = new PersistenceService();
    private final ClassService classService = new ClassService();
    private final AssessmentService assessmentService = new AssessmentService();

    public AcademicSystemController(Stage stage) {
        this.stage = stage;
    }

    // ==========================================================
    // INICIALIZAÇÃO
    // ==========================================================

    public void iniciar() {
        System.out.println("Iniciando interface gráfica...");
        mostrarTelaLogin();
    }

    public void mostrarTelaLogin() {
        new LoginMenuApp(stage, this).exibir();
    }

    // ==========================================================
    // AUTENTICAÇÃO
    // ==========================================================

    public User processarLogin(String email, String senha) {

        try {

            User user = authenticate.login(email, senha);

            System.out.println(
                    "Bem-vindo, " +
                            user.getUsername() +
                            " [" +
                            user.getRole() +
                            "]"
            );

            direcionarParaMenu(user);

            return user;

        } catch (AuthenticationExceptionAcademic e) {

            throw new RuntimeException(
                    "Falha no login: " + e.getMessage()
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao ler arquivo: " + e.getMessage()
            );
        }
    }

    public void direcionarParaMenu(User user) {
        user.getMenuApp().carregarMenu(stage, this);
    }

    // ==========================================================
    // TURMAS
    // ==========================================================

    public void registrarTurma(Classroom classroom) {
        classService.registerClassroom(classroom);
    }

    public List<Classroom> listarTurmas() {
        return AcademicSystem
                .getInstance()
                .getClassrooms();
    }

    // ==========================================================
    // AVALIAÇÕES
    // ==========================================================

    public void registrarAvaliacao(
            Long classroomID,
            int tipo,
            double value,
            double weight) {

        assessmentService.registerAssessment(
                classroomID,
                tipo,
                value,
                weight
        );
    }

    // ==========================================================
    // RELATÓRIOS
    // ==========================================================

    public String gerarRelatorioResumo() {
        return reportService.generateClassAssessmentSummaryReport();
    }

    public String gerarRelatorioPeso() {
        return reportService.generateAssessmentWeightReport();
    }

    public String gerarRelatorioPersistencia() {
        return reportService.generatePersistenceConfigurationReport();
    }

    // ==========================================================
    // PERSISTÊNCIA
    // ==========================================================

    public void configurarPersistencia(PersistenceType tipo) {
        persistenceService.setPersistenceType(tipo);
    }

    public PersistenceType getPersistenceType() {
        return persistenceService.getPersistenceType();
    }

    public void salvarDados() throws IOException {
        persistenceService.save();
    }

}