package br.com.jcv.treinadorpro.corelayer.enums;

public enum ExecutionMethodEnum {
    traditionalSet("Traditional Set", "Série Tradicional", "Serie Tradicional"),
    dropSet("Drop Set", "Drop Set", "Drop Set"),
    biSet("Bi-set", "Bi-set", "Bi-set"),
    triSet("Tri-set", "Tri-set", "Tri-set"),
    giantSet("Giant Set", "Série Gigante", "Serie Gigante"),
    restPause("Rest-Pause", "Rest-Pause", "Pausa Descanso"),
    isometric("Isometric Hold", "Isometria", "Isometría"),
    timeUnderTension("Time Under Tension", "Tempo Sob Tensão", "Tiempo Bajo Tensión"),
    partialReps("Partial Repetitions", "Repetições Parciais", "Repeticiones Parciales"),
    negativeReps("Negative (Eccentric)", "Negativa (Excêntrica)", "Negativa (Excéntrica)"),
    pyramidSet("Pyramid Set", "Série Pirâmide", "Serie Piramidal"),
    superSlow("Super Slow", "Execução Lenta", "Ejecución Lenta"),
    preExhaustion("Pre-exhaustion", "Pré-exaustão", "Pre-agotamiento"),
    postExhaustion("Post-exhaustion", "Pós-exaustão", "Post-agotamiento"),
    clusterSet("Cluster Set", "Série Cluster", "Serie de Clúster"),
    circuitTraining("Circuit Training", "Treino em Circuito", "Entrenamiento en Circuito"),
    fst7("FST-7", "FST-7", "FST-7"),

    circuito("Circuit", "Circuito", "Circuito"),
    serie("Set", "Série", "Serie"),
    piramide("Pyramid", "Pirâmide", "Pirámide"),
    cluster("Cluster", "Cluster", "Clúster"),
    livre("Free", "Livre", "Libre"),
    tempoCorrente("Time Flow", "Tempo Corrente", "Tiempo Corriente"),
    isometrico("Isometric", "Isométrico", "Isométrico"),
    isocinetico("Isokinetic", "Isocinético", "Isocinético"),
    pliometrico("Plyometric", "Pliométrico", "Pliométrico"),
    emom("EMOM (Every Minute On the Minute)", "EMOM (Cada Minuto)", "EMOM (Cada Minuto)"),
    amrap("AMRAP (As Many Reps As Possible)", "AMRAP (Máx. Reps Possível)", "AMRAP (Máx. Reps Posibles)"),
    tabata("Tabata", "Tabata", "Tabata"),
    descansoAtivo("Active Rest", "Descanso Ativo", "Descanso Activo"),
    intervalo("Interval", "Intervalo", "Intervalo"),
    tempoAlvo("Target Time", "Tempo Alvo", "Tiempo Objetivo"),
    repMaxima("Max Repetition", "Repetição Máxima", "Repetición Máxima"),
    combinado("Combined", "Combinado", "Combinado"),
    funcional("Functional", "Funcional", "Funcional"),
    resistido("Resisted", "Resistido", "Resistido"),
    semCarga("No Load", "Sem Carga", "Sin Carga");

    private final String nameEn;
    private final String namePt;
    private final String nameEs;

    ExecutionMethodEnum(String nameEn, String namePt, String nameEs) {
        this.nameEn = nameEn;
        this.namePt = namePt;
        this.nameEs = nameEs;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNamePt() {
        return namePt;
    }

    public String getNameEs() {
        return nameEs;
    }
}
