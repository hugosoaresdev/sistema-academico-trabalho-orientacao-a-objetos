package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Classe-mãe (abstrata) de toda AVALIAÇÃO do sistema.
 *
 * O que é "abstract"?
 *   Uma classe abstrata é um molde que NÃO pode ser instanciado diretamente.
 *   Você não escreve "new Assessment(...)" — não faz sentido criar uma
 *   "avaliação genérica". Você cria um tipo concreto: new Exam(...),
 *   new Seminar(...), etc. A Assessment só existe para REUNIR o que é
 *   comum a todos esses tipos (id, título, valor, peso) em um único lugar,
 *   evitando repetir os mesmos campos em cada subclasse.
 *
 * Herança e polimorfismo (os conceitos centrais aqui):
 *   - Herança: Exam, PracticalAssignment, Seminar e Assignment ESTENDEM
 *     Assessment. Cada uma "é uma" Assessment e herda automaticamente os
 *     campos title, value e weight definidos aqui.
 *   - Polimorfismo: a Classroom guarda uma List<Assessment>. Ela não precisa
 *     saber se cada item é um Exam ou um Seminar — trata todos como
 *     "Assessment". É isso que permite registrar tipos diferentes de
 *     avaliação na mesma lista sem código duplicado.
 *
 * Campos (os atributos que TODA avaliação tem, segundo a US-2361):
 *   - assessmentID: identidade da avaliação.
 *   - title:        nome/título da avaliação (ex: "Prova 1").
 *   - value:        o valor/nota da avaliação (US-2361 AC3 fala em "value").
 *   - weight:       o peso da avaliação na composição da nota da turma
 *                   (US-2361 AC3 fala em "weight").
 *
 * Sobre as anotações @NotBlank/@Positive (Jakarta Bean Validation):
 *   Elas declaram as regras de validação direto no domínio. Quem valida
 *   (o AssessmentService) usa essas anotações para rejeitar dados inválidos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "assessmentID")
public abstract class Assessment {

    @NotNull
    private Long assessmentID;

    @NotBlank
    private String title;

    @PositiveOrZero
    private double value;

    @Positive
    private double weight;

    /**
     * Devolve o "tipo" da avaliação como texto (ex: "Exam", "Seminar").
     *
     * Aqui aparece o polimorfismo de novo: cada subclasse pode RESPONDER
     * de um jeito diferente. A implementação padrão devolve o nome simples
     * da classe, então um Exam diz "Exam", um Seminar diz "Seminar", etc.,
     * sem que a gente precise escrever isso em cada uma.
     *
     * É útil para relatórios e para o arquivo de persistência mostrarem
     * o tipo da avaliação.
     */
    public String getType() {
        return this.getClass().getSimpleName();
    }
}